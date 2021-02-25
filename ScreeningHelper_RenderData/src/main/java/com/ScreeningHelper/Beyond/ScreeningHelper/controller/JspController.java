package com.ScreeningHelper.Beyond.ScreeningHelper.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ScreeningHelper.Beyond.ScreeningHelper.repository.MongoPykrxInfo;
import com.ScreeningHelper.Beyond.ScreeningHelper.service.PykrxInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JspController {
    private PykrxInfoService prkrxInfoService;
    private Integer a = 0;

    @Autowired
    public void setPykrxInfoService(PykrxInfoService pykrxInfoService) {
        this.prkrxInfoService = pykrxInfoService;
    }

    //stock read
    @RequestMapping(value = "/checkTest/read")
    public String stockRead(Model model) {
        model.addAttribute("number",a.toString());
        return "test";
    }

    //stock update
    @RequestMapping(value = "/checkTest/update", method=RequestMethod.POST)
    @ResponseBody
    public String stockUpdate(Model model) {
        a += 1;
        String str = a.toString();
        return str;
    }

    //게시글 리스트 조회
    @RequestMapping(value = "/checkTest")
    public String checkTest(@RequestParam Map<String, Object> paramMap, Model model) {

        return "checkTest";
    }

    //AJAX 호출 (게시글 등록, 수정)
    @RequestMapping(value="/checkTest/save", method=RequestMethod.POST)
    @ResponseBody
    public Object checkTestSave(
            @RequestParam(value="fruitList[]") List<String> fruitList,
            @RequestParam(value="user") String user
    ) {

        System.out.println("=user=");
        System.out.println(user);

        System.out.println("=fruit=");
        for(String fruit : fruitList) {
            System.out.println(fruit);
        }

        //리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();

        //성공했다고 처리
        retVal.put("code", "OK");
        retVal.put("message", "등록에 성공 하였습니다.");

        return retVal;

    }

}
