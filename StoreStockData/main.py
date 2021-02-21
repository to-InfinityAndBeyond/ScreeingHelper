from pymongo import MongoClient
from pykrx import stock
from pprint import pprint

# 스크립트를 실행하려면 여백의 녹색 버튼을 누릅니다.
if __name__ == '__main__':
    client = MongoClient("mongodb://localhost:27017/")
    db = client['test']
    collection = db['pykrxInfo']
    collection.drop()

    df = stock.get_market_fundamental_by_ticker("20210201")
    df_dict = df.to_dict("index")
    i = 0
    for key in dict(df_dict).keys():
        value = df_dict[key]
        # print(key, ":", df_dict[key])

        if (dict(value)['PER'] == 0.0):
            roe = 0.0
        else:
            roe = (dict(value)['PBR'] / dict(value)['PER']) * 100

        collection.insert_one({"_id": key, "BPS": dict(value)['BPS'],
                               "PER": dict(value)['PER'], "PBR": dict(value)['PBR'],
                               "EPS": dict(value)['EPS'], "DIV": dict(value)['DIV'],
                               "DPS": dict(value)['DPS'],
                               "ROE": roe})

    # dox = collection.find().sort('ROE', -1)
    # cursor = collection.find({})

    # for doc in cursor:
    #     pprint(doc)
    #
    # for x in dox:
    #     print(x)
    #
    # print(type(dox))
    # print(type(collection))
