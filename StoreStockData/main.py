from pymongo import MongoClient
from pykrx import stock
from pprint import pprint
import datetime

# 스크립트를 실행하려면 여백의 녹색 버튼을 누릅니다.
if __name__ == '__main__':
    now = datetime.datetime.now()
    date = now.strftime("%Y%m%d")
    client = MongoClient("mongodb://localhost:27017/")
    db = client['test']
    collection = db['pykrxInfo']
    collection_sv = db['stockValue']

    # collection.drop()
    # collection_sv.drop()

    # Date
    prevDay = now.day
    prevMin = now.minute
    prevSec = now.second
    day = 0
    min = 0
    sec = 0
    while(1):
        if(prevDay != day):
            df = stock.get_market_fundamental_by_ticker(date)
            df_dict = df.to_dict("index")
            for key in dict(df_dict).keys():
                value = df_dict[key]
                #value_sv = df_sv_dict[key]

                if (dict(value)['PER'] == 0.0):
                    roe = 0.0
                else:
                    roe = (dict(value)['PBR'] / dict(value)['PER']) * 100

                collection.update_one({"_id": key},{"$set":{
                                       "name": stock.get_market_ticker_name(key), "BPS": dict(value)['BPS'],
                                       "PER": dict(value)['PER'], "PBR": dict(value)['PBR'],
                                       "EPS": dict(value)['EPS'], "DIV": dict(value)['DIV'],
                                       "DPS": dict(value)['DPS'],
                                       "ROE": roe}})
            prevDay = now.day
            print("PYKRX INFO Updated", prevDay, day)

        if(prevSec != sec):
            df_sv = stock.get_market_ohlcv_by_ticker(date)
            df_sv_dict = df_sv.to_dict("index")
            for key in dict(df_sv_dict).keys():
                value = df_sv_dict[key]

                collection_sv.update_one({"_id": key},{"$set":{
                                       "curPrice": dict(value)['시가'],
                                       "highPrice": dict(value)['고가'], "lowPrice": dict(value)['저가'],
                                       "endPrice": dict(value)['종가'], "volume": dict(value)['거래량'],
                                       "transaction": dict(value)['거래대금'], "fluctuation": dict(value)['등락률']}})
            prevSec = now.second
            print("PYKRX SV Updated" ,prevSec ,sec)

        now = datetime.datetime.now()
        day = now.day
        min = now.minute
        sec = now.second



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
