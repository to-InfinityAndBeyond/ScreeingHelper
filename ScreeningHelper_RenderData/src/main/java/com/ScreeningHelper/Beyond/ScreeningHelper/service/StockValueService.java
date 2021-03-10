package com.ScreeningHelper.Beyond.ScreeningHelper.service;

import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxInfo;
import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxRepository;
import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoStockValue;
import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoStockValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StockValueService {

    MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    public MongoStockValueRepository mongoStockValueRepository;

    public void printById(String id) {
        Optional<MongoStockValue> mongoStockValue = mongoStockValueRepository.findById(id);
    }

    public List<MongoStockValue>  selectStockValue(List<MongoPykrxInfo> mongoPykrxInfos) {
        List<MongoStockValue> mongoStockValues = new ArrayList<>();
        for(MongoPykrxInfo info: mongoPykrxInfos) {
            mongoStockValues.add((mongoStockValueRepository.findById(info.getId())).orElse(new MongoStockValue()));
        }


//        Collections.sort(mongoStockValues, new Comparator<MongoStockValue>() {
//            @Override
//            public int compare(MongoPykrxInfo o1, MongoPykrxInfo o2) {
//                return (int)(o2.getROE()*100 - o1.getROE()*100);
//            }
//        });
        return mongoStockValues;
    }
}
