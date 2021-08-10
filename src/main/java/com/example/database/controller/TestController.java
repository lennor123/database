package com.example.database.controller;
import com.example.database.dds.DataSource;
import com.example.database.entity.Test1;
import com.example.database.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户控制器
 */
@Controller
public class TestController {

    @Autowired
    private TestService testService;


    @DataSource(value="mysmart")
    @RequestMapping(value="/findAll1")
    @ResponseBody
    public List<Test1> findAll1() {
        return testService.findAll();
    }

    @DataSource(value="myresource")
    @RequestMapping(value="/findAll2")
    @ResponseBody
    public List<Test1> findAll2() {
        return testService.findAll();
    }

}