package com.mojo.userservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mojo
 * @description: TODO
 * @date 2023/7/22 0022 11:12
 */
@RestController
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "hello world";
    }
}
