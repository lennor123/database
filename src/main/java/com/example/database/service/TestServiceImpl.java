package com.example.database.service;

import com.example.database.dao.TestDao;
import com.example.database.entity.Test1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TestServiceImpl implements TestService{

    @Autowired
    TestDao testDao;

    @Override
    public List<Test1> findAll() {
        return testDao.findAll();
    }
}
