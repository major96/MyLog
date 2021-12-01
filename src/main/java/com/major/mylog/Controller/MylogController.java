package com.major.mylog.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MylogController {
    
    @GetMapping
    public String index(){
        return "index";
    }

}
