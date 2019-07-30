package com.example.ioc.controller;

import com.example.ioc.service.IocService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IocController implements InitializingBean{

    @Autowired
    IocService iocService;


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
