package com.example.database.service;

import com.example.database.dao.LoginDao;
import com.example.database.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private LoginDao loginDao;

    @Override
    public User loginInfo(String User ,String Key) {

        return loginDao.getInfo(User,Key);
    }

    @Override
    public List<Repository> findInfo() {
        List<Repository> list = loginDao.findInfo();
        return list;
    }

    @Override
    public List<Table> alltablename(String tableName) {
        List<Table> list = loginDao.alltablename(tableName);
        return list;
    }

    @Override
    public List<Det> detail(String anyName) {
        List<Det> detlist = loginDao.detail(anyName);
        return detlist;
    }

    @Override
    public Insert insertviewtable(Insert insert) {
        String repositoryId = insert.getRepositoryId();
        int num = Integer.parseInt(repositoryId);
        insert.setNewRepositoryId(num);
        loginDao.insertsetp1(insert);

        System.out.println(insert.getId());
        int b = loginDao.insertsetp2(insert);
        return insert;
    }

    @Override
    public Repository findRepositoryInfo(int id) {
        Repository repository = loginDao.findRepositoryInfo(id);
        return repository;
    }

    @Override
    public List<Field> findFieldsInfo(int id) {
        List<Field> fieldlist = loginDao.findFieldsInfo(id);
        return fieldlist;
    }

    @Override
    public List<Repository> findDataModelInfo() {
        List<Repository> rlist = loginDao.findDataModelInfo();
        return rlist;
    }

    @Override
    public List<Field> findFieldInfo(int id) {
        List<Field> flist = loginDao.findFieldInfo(id);
        return flist;
    }


}
