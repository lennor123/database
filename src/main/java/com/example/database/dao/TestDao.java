package com.example.database.dao;

import com.example.database.entity.Test1;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestDao {
    List<Test1> findAll();
}
