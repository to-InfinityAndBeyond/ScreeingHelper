package com.ScreeningHelper.Beyond.ScreeningHelper.controller;

import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxInfo;
import com.ScreeningHelper.Beyond.ScreeningHelper.service.PykrxInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PykrxInfoController {
    private PykrxInfoService prkrxInfoService;

    @Autowired
    public void setPykrxInfoService(PykrxInfoService pykrxInfoService) {
        this.prkrxInfoService = pykrxInfoService;
    }

    @RequestMapping("/Info")
    public String pykrxInfo(Model model) {
        List<MongoPykrxInfo> mongoPykrxInfos = prkrxInfoService.selectPykrxInfo();
        model.addAttribute("pykrxInfo", mongoPykrxInfos);
        return "Info";
    }
}
