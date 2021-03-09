from pymongo import MongoClient
from pykrx import stock
from pprint import pprint
import datetime
import pandas as pd
import requests
import xml.etree.ElementTree as ET
import zipfile
import io
import json
from pandas import json_normalize
import OpenDartReader

api_key = '219325ecdf2fead76e32c51829c914274336d3fb'
dart = OpenDartReader(api_key)
report_code = '11011'
fs_div = 'CFS'

def get_debt_ratio(input_df):
    if not len(input_df):
        return None

    df = input_df[0]

    fs_df = df.loc[df['sj_div'] == 'BS']

    debtratio = fs_df.loc[
        (fs_df['account_id'] == 'ifrs-full_Liabilities') | (fs_df['account_id'] == 'ifrs-full_Equity'), ['account_nm',
                                                                                                         'thstrm_amount',
                                                                                                         'frmtrm_amount',
                                                                                                         'bfefrmtrm_amount']]

    debtratio = debtratio.set_index('account_nm')
    debtratio[['thstrm_amount', 'frmtrm_amount', 'bfefrmtrm_amount']] = debtratio[['thstrm_amount', 'frmtrm_amount',
                                                                                   'bfefrmtrm_amount']].apply(
        pd.to_numeric) / 100000000
    debtratio = debtratio.T
    debtratio = debtratio.reset_index(drop=False)
    debtratio.rename(columns={'index': '구분'}, inplace=True)
    debtratio['구분'] = debtratio['구분'].replace('thstrm_amount', '당기').replace('frmtrm_amount', '전기').replace(
        'bfefrmtrm_amount', '전전기')
    debtratio['부채비율'] = debtratio['부채총계'] / debtratio['자본총계'] * 100
    debtratio['부채비율'] = debtratio['부채비율'].apply(lambda x: round(x, 2))
    return debtratio

def opendart_finance(stock_code, target_year, report_code, fs_div):
    #전체 고유번호 구하기
    url = 'https://opendart.fss.or.kr/api/corpCode.xml'
    params = { 'crtfc_key': api_key, }

    r = requests.get(url, params=params)
    try:
        tree = ET.XML(r.content)
        status = tree.find('status').text
        message = tree.find('message').text
        if status != '000':
            raise ValueError({'status': status, 'message': message})
    except ET.ParseError as e:
        pass

    zf = zipfile.ZipFile(io.BytesIO(r.content))
    xml_data = zf.read('CORPCODE.xml')

    tree = ET.XML(xml_data)
    all_records = []

    element = tree.findall('list')
    for i, child in enumerate(element):
        record = {}
        for i, subchild in enumerate(child):
            record[subchild.tag] = subchild.text
        all_records.append(record)

    corp_codes = pd.DataFrame(all_records)

    # 종목코드와 매칭되는 고유번호 구하기
    corp_code = -1
    df = corp_codes[corp_codes['stock_code'] == stock_code]
    if df.empty:
        pass
    else:
        corp_code = df.iloc[0]['corp_code']

    final_df =[]
    #재무제표 불러오기
    if corp_code == -1:
        return final_df

    url = 'https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json'
    params = {
        'crtfc_key': api_key,
        'corp_code': corp_code,
        'bsns_year': target_year,   # 사업년도
        'reprt_code': report_code, # "11011": 사업보고서
        'fs_div': fs_div
    }

    r = requests.get(url, params=params)
    jo = json.loads(r.text)
    if jo['status'] == '013':
        pass
    else:
        df = json_normalize(jo, 'list')
        final_df.append(df)

    return final_df

# 스크립트를 실행하려면 여백의 녹색 버튼을 누릅니다.
if __name__ == '__main__':

    # 시간 변수
    day = 0
    min = 0
    sec = 0
    year = 0
    now = datetime.datetime.now()
    date = now.strftime("%Y%m%d")
    prevDay = now.day
    prevMin = now.minute
    prevSec = now.second
    prevYear = now.year

    # MongoDB 변수
    client = MongoClient("mongodb://localhost:27017/")
    db = client['test']
    collection = db['pykrxInfo']
    collection_sv = db['stockValue']
    collection_fs = db['financialStatement']

    # Loop
    #while (1):
    now = datetime.datetime.now()
    date = now.strftime("%Y%m%d")

    if (prevDay != day):
        df = stock.get_market_fundamental_by_ticker(date)
        df_dict = df.to_dict("index")
        count = 0
        for key in dict(df_dict).keys():
            count += 1
            print("["+str(count)+"/"+str(len(dict(df_dict)))+"] " + "Updating pykrx/financial_statement")
            value = df_dict[key]
            # value_sv = df_sv_dict[key]

            if (dict(value)['PER'] == 0.0):
                roe = 0.0
            else:
                roe = (dict(value)['PBR'] / dict(value)['PER']) * 100

            debt_ratio = None
            target_year = now.year + 1
            while debt_ratio is None:
                target_year -= 1
                df = opendart_finance(key, target_year, report_code, fs_div)
                debt_ratio = get_debt_ratio(df)
                if target_year < now.year - 5:
                    break

            collection.update_one({"_id": key}, {"$set": {
                "name": stock.get_market_ticker_name(key), "BPS": dict(value)['BPS'],
                "PER": dict(value)['PER'], "PBR": dict(value)['PBR'],
                "EPS": dict(value)['EPS'], "DIV": dict(value)['DIV'],
                "DPS": dict(value)['DPS'],
                "ROE": roe}}, upsert=True)


            _valid = 0
            _year = 0
            _this = 0
            _last = 0
            _lastlast = 0
            if debt_ratio is not None:
                _valid = 1
                _year = target_year
                _this = debt_ratio['부채비율'][0]
                _last = debt_ratio['부채비율'][1]
                _lastlast = debt_ratio['부채비율'][2]

            collection_fs.update_one({"_id": key}, {"$set":{
                "isValid": _valid,
                "year": _year,
                "당기_부채비율": _this,
                "전기_부채비율": _last,
                "전전기_부채비율": _lastlast,
            }}, upsert=True)

            print("[debug] _valid : "+str(_valid)+
                  ", _year : "+str(_year)+
                  ", _this : "+str(_this)+
                  ", _last : " + str(_last) +
                  ", _lastlast : " + str(_lastlast)
                  )

        prevDay = now.day
        print("PYKRX INFO / FINANCIAL STATMENT Updated", prevDay, day)

    if (prevSec != sec):
        df_sv = stock.get_market_ohlcv_by_ticker(date)
        df_sv_dict = df_sv.to_dict("index")

        for key in dict(df_sv_dict).keys():
            value = df_sv_dict[key]
            print("[" + str(count) + "/" + str(len(dict(df_dict))) + "] " + "Updating pykrx sv")

            collection_sv.update_one({"_id": key}, {"$set": {
                "curPrice": dict(value)['시가'],
                "highPrice": dict(value)['고가'], "lowPrice": dict(value)['저가'],
                "endPrice": dict(value)['종가'], "volume": dict(value)['거래량'],
                "transaction": dict(value)['거래대금'], "fluctuation": dict(value)['등락률']}}, upsert=True)
        prevSec = now.second
        print("PYKRX SV Updated", prevMin, min)


    # cursor = collection.find({})
    # cursor_sv = collection_sv.find({})
    #
    # for doc in cursor:
    #     pprint(doc)
    # for doc in cursor_sv:
    #     pprint(doc)
    #
    # for x in dox:
    #     print(x)
    #
    # print(type(dox))
    # print(type(collection))
