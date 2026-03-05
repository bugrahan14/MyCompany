package com.bugrahan.company.demosecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    @GetMapping("/showMyLoginPage")
    public String  showMyLoginPage(){

        return "custom-login";
    }


}
