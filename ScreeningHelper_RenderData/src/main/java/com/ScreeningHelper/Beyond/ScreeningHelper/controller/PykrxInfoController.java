package com.ScreeningHelper.Beyond.ScreeningHelper.controller;

import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxInfo;
import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoStockValue;
import com.ScreeningHelper.Beyond.ScreeningHelper.service.PykrxInfoService;
import com.ScreeningHelper.Beyond.ScreeningHelper.service.StockValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PykrxInfoController {

    private PykrxInfoService prkrxInfoService;
    private StockValueService stockValueService;
    private List<MongoPykrxInfo> mongoPykrxInfos;

    @Autowired
    public void setPykrxInfoService(PykrxInfoService pykrxInfoService, StockValueService stockValueService) {
        this.prkrxInfoService = pykrxInfoService;
        this.stockValueService = stockValueService;
        this.mongoPykrxInfos = prkrxInfoService.selectPykrxInfo();
    }

    // read All Stock Data
    @RequestMapping(value = "/Info/read")
    public String stockRead(Model model) {
        mongoPykrxInfos = prkrxInfoService.selectPykrxInfo();
        model.addAttribute("pykrxInfo", mongoPykrxInfos);
        return "updateStock";
    }

    // read only Pykrx Info
    @RequestMapping(value = "/Info/pykrxInfo/read")
    public String pykrxRead(Model model) {
        mongoPykrxInfos = prkrxInfoService.selectPykrxInfo();
        model.addAttribute("pykrxInfo", mongoPykrxInfos);
        return "PykrxInfo";
    }

    // read only Pykrx Info
    @RequestMapping(value = "/Info/stockValue/read")
    public String stockValueRead(Model model) {
        List<MongoStockValue>  mongoStockValues = stockValueService.selectStockValue(mongoPykrxInfos);
        model.addAttribute("stockValue", mongoStockValues);
        return "StockValue";
    }

    // update pykrxInfo
    @RequestMapping(value = "/Info/update", method=RequestMethod.POST)
    public String stockUpdate(Model model) {
        List<MongoPykrxInfo> mongoPykrxInfos = prkrxInfoService.selectPykrxInfo();
        System.out.print(".");
        model.addAttribute("pykrxInfo", mongoPykrxInfos);
        return "updateStock :: #stockList";
    }

    // update StockValue
    @RequestMapping(value = "/Info/update/sv", method=RequestMethod.POST)
    public String stockUpdate_sv(Model model) {
        List<MongoStockValue>  mongoStockValues = stockValueService.selectStockValue(mongoPykrxInfos);
        System.out.print("/");
        model.addAttribute("stockValue", mongoStockValues);
        return "updateStock :: #stockList_sv";
    }

    // update Only StockValue
    @RequestMapping(value = "/Info/update/only/sv", method=RequestMethod.POST)
    public String onlystockUpdate_sv(Model model) {
        List<MongoStockValue>  mongoStockValues = stockValueService.selectStockValue(mongoPykrxInfos);
        model.addAttribute("stockValue", mongoStockValues);
        return "StockValue :: #stockList_sv";
    }

    // update pykrxInfo
    @RequestMapping(value = "/Info/update/only", method=RequestMethod.POST)
    public String onlystockUpdate(Model model) {
        List<MongoPykrxInfo> mongoPykrxInfos = prkrxInfoService.selectPykrxInfo();
        model.addAttribute("pykrxInfo", mongoPykrxInfos);
        return "PykrxInfo :: #stockList";
    }
}
