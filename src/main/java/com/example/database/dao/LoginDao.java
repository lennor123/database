package com.example.database.dao;
import com.example.database.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@org.springframework.stereotype.Repository
public interface LoginDao {
   User getInfo(String User, String Key );

   List<Repository> findInfo();

   List<Table> alltablename(String tableName);

   List<Det> detail(String anyName);

   int insertsetp1(@Param("insert") Insert insert);

   int insertsetp2(Insert insert);

   Repository findRepositoryInfo(int id);

   List<Field> findFieldsInfo(int id);

   List<Repository> findDataModelInfo();

   List<Field> findFieldInfo(int id);
}
