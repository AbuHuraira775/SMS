package com.ah.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author "Abu Huraira"
 **/

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(){

        return "login";
    }
}
