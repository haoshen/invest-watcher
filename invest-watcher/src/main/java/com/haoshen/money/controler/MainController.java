package com.haoshen.money.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(value="/")
    public String welcome() {
        return "forward:about.html";
    }

    @RequestMapping(value="/test")
    public String test() {
        return "OK";
    }

}
