package com.ScreeningHelper.Beyond.ScreeningHelper.controller;

import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxInfo;
import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoStockValue;
import com.ScreeningHelper.Beyond.ScreeningHelper.service.PykrxInfoService;
import com.ScreeningHelper.Beyond.ScreeningHelper.service.StockValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PykrxInfoController {

    private PykrxInfoService prkrxInfoService;
    private StockValueService stockValueService;

    @Autowired
    public void setPykrxInfoService(PykrxInfoService pykrxInfoService, StockValueService stockValueService) {
        this.prkrxInfoService = pykrxInfoService;
        this.stockValueService = stockValueService;
    }

    // read pykrx
    @RequestMapping(value = "/Info/read")
    public String stockRead(Model model) {
        List<MongoPykrxInfo> mongoPykrxInfos = prkrxInfoService.selectPykrxInfo();
        model.addAttribute("pykrxInfo", mongoPykrxInfos);
        return "updateStock";
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
        List<MongoStockValue> mongoStockValues = stockValueService.selectStockValue();
        System.out.print("/");
        model.addAttribute("stockValue", mongoStockValues);
        return "updateStock :: #stockList_sv";
    }
}
