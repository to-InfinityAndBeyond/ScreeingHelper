package com.ScreeningHelper.Beyond.ScreeningHelper.service;

import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxInfo;
import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoStockValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class StockValueService {

    MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<MongoStockValue> selectStockValue() {
        List<MongoStockValue> mongoStockValues = mongoTemplate.findAll(MongoStockValue.class);
//        Collections.sort(mongoStockValues, new Comparator<MongoStockValue>() {
//            @Override
//            public int compare(MongoPykrxInfo o1, MongoPykrxInfo o2) {
//                return (int)(o2.getROE()*100 - o1.getROE()*100);
//            }
//        });
        return mongoStockValues;
    }
}
