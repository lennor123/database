package com.example.database;

import org.junit.Test;

import java.sql.*;

public class DatabaseApplicationTests {

    public static final String URL = "jdbc:mysql://192.168.1.209:3306/mysmart?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "root";

    @Test
    public void test() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        try{
            //1.加载驱动程序
//        Class.forName("com.mysql.jdbc.Driver");
            //2. 获得数据库连接

            //3.操作数据库，实现增删改查
            String sql = "SELECT * FROM data_model where id = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, 38);
//        Statement stmt = conn.createStatement();
            ResultSet rs = preparedStatement.executeQuery();
            //如果有数据，rs.next()返回true
            while(rs.next()){
                System.out.println(rs.getInt("id"));
            }
        }finally {
            if(conn!=null){
                conn.close();
            }
        }
    }

}
