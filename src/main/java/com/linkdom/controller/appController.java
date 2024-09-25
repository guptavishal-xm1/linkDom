package com.linkdom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class appController {

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "app/index";
    }

    @RequestMapping("linkCreate")
    public String createLink(){
        return "createlink/index";
    }

}
