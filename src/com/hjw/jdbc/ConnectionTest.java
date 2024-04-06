package com.hjw.jdbc;

import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

public class ConnectionTest {

    @Test
    public void test() throws SQLException, ClassNotFoundException {
        // 1.加载驱动
        // 1.1 加载驱动方式一，registerDriver方法中会去调用new Driver，相当于执行两次注册驱动的方法
        // DriverManager.registerDriver(new Driver());

        // 1.2 加载驱动方式二，固定加载MySQL的驱动，不灵活
        // new Driver();

        // 1.3 通过反射的方法执行1次驱动注册，且可动态传入驱动的类目，灵活（常用）
        /*
            static {
                try {
                    DriverManager.registerDriver(new Driver());
                } catch (SQLException var1) {
                    throw new RuntimeException("Can't register driver!");
                }
            }
         */
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.获取连接
        // 2.1 获取连接 getConnection(String url)
        // url地址格式：jdbc:数据库厂商名://ip地址:port/数据库名
        //              jdbc:mysql://127.0.0.1:3306/test
        // 一个参数：
        // String URL: URl可以携带目标地址，可以通过?分割，在后面key=value&key=value形式传递参数
        //                   jdbc:mysql:///day01?user=root&password=123456
        // 扩展路径参数(了解):
        //        *    8.0.25以后，自动识别时区 serverTimezone=Asia/Shanghai 不用添加
        //        *    8版本以后， 默认使用utf-8格式, useUnicode=true&characterEncoding=utf8&useSSL=true 不用添加
        //        *    serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=true
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jdbc-test?user=root&password=HJW413608");

        // 2.2 getConnection(String url,
        //        String user, String password)
        // url地址格式：jdbc:数据库厂商名://ip地址:port/数据库名
        // user：用户名
        // password：密码
        // Connection connection1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jdbc-test", "root", "HJW413608");

        // 2.3 getConnection(String url,
        //        java.util.Properties info)
        // url地址格式：jdbc:数据库厂商名://ip地址:port/数据库名
        // info：
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "HJW413608");
        // Connection connection2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jdbc-test", info);

        // 3. 获取statement
        Statement statement = connection.createStatement();

        // 4. 准备SQL语句
        String sql = "SELECT id, account, nickname, password  FROM t_user;";

        // 5.执行SQL语句
        /*
         *  sql分类： DDL(容器创建,修改,删除) DML(插入,修改,删除) DQL(查询) DCL(权限控制) TPL(事务控制)
         *  ResultSet 结果集对象 = executeQuery(DQL语句)
         *  int响应行数  = executeUpdate(非DQL语句)
         */
        ResultSet resultSet = statement.executeQuery(sql);

        // 6.结果集解析
        /*
         *
         * TODO:1.需要理解ResultSet的数据结构和小海豚查询出来的是一样，需要在脑子里构建结果表！
         * TODO:2.有一个光标指向的操作数据行，默认指向第一行的上边！我们需要移动光标，指向行，在获取列即可！
         *        boolean = next()
         *              false: 没有数据，也不移动了！
         *              true:  有更多行，并且移动到下一行！
         *       推荐：推荐使用if 或者 while循环，嵌套next方法，循环和判断体内获取数据！
         *       if(next()){获取列的数据！} ||  while(next()){获取列的数据！}
         *
         * TODO：3.获取当前行列的数据！
         *         get类型(int columnIndex | String columnLabel)
         *        列名获取  //lable 如果没有别名，等于列名， 有别名label就是别名，他就是查询结果的标识！
         *        列的角标  //从左到右 从1开始！ 数据库全是从1开始！
         */
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String account = resultSet.getString(2);
            String nickname = resultSet.getString("nickname");
            String password = resultSet.getString(4);
            System.out.println("id: " +  id + ", account: " + account + ", nickname: " + nickname + ", password: " + password);
        }

        // 7.关闭连接
        resultSet.close();
        statement.close();
        connection.close();
    }

}
