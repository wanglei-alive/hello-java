package com.cloud.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/", produces = {APPLICATION_JSON_VALUE})
public class Hello {

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    String index(){
        return "Hello world!";
    }

}