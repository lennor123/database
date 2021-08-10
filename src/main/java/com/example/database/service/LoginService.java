package com.example.database.service;

import com.example.database.entity.*;

import java.util.List;

public interface LoginService {
    User loginInfo(String User,String Key);

    List<Repository> findInfo();

    List<Table> alltablename(String tableName);

    List<Det> detail(String anyName);

    Insert insertviewtable(Insert insert );

    Repository findRepositoryInfo(int id);

    List<Field> findFieldsInfo(int id);

    List<Repository> findDataModelInfo();

    List<Field> findFieldInfo(int id);
}
