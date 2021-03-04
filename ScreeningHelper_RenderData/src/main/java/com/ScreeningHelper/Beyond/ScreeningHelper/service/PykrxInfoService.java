package com.ScreeningHelper.Beyond.ScreeningHelper.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxInfo;
import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class PykrxInfoService {
//    MongoRepository mongoRepository;
    MongoTemplate mongoTemplate;

    @Autowired
    public MongoPykrxRepository mongoPykrxRepository;

    public void printById(String id) {
        Optional<MongoPykrxInfo> mongoPykrxInfo = mongoPykrxRepository.findById(id);
    }

    @Autowired
//    public void setMongoRepository(MongoRepository mongoRepository) {
//        this.mongoRepository = mongoRepository;
//    }
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

//    public List<MongoPykrxInfo> selectPykrxInfo() {
//        List<MongoPykrxInfo> pykrxInfo = mongoRepository.findAll();
//        return pykrxInfo;
//    }
    public List<MongoPykrxInfo> selectPykrxInfo() {
        List<MongoPykrxInfo> mongoPykrxInfoList = mongoTemplate.findAll(MongoPykrxInfo.class);
        Collections.sort(mongoPykrxInfoList, new Comparator<MongoPykrxInfo>() {
            @Override
            public int compare(MongoPykrxInfo o1, MongoPykrxInfo o2) {
                return (int)(o2.getROE()*100 - o1.getROE()*100);
            }
        });
        return mongoPykrxInfoList;
    }
}
