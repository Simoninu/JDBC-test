package com.hjw.jdbc;

import java.sql.*;
import java.util.Scanner;

/**
 * 测试statement，并引出prepareStatement
 * 场景：模拟登录,控制台输入账号和密码,判断是否登陆成功成功!
 * 存在问题：
 * 1. SQL语句需要字符串拼接,比较麻烦（示例代码在：执行SQL语句 [动态SQL语句,需要字符串拼接]）
 * 2. 只能拼接字符串类型,其他的数据库类型无法处理
 * 3. 可能发生注入攻击
 * > 动态值充当了SQL语句结构,影响了原有的查询结果!
 */
public class StatementTest {

    /**
     * 思路分析：
     * 1. 输入账号密码
     * 2. 根据输入的账号密码，查询数据库是否存在对应数据
     * 3. 如果存在，登录成功
     * 4. 不存在，登录失败
     */
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        // 1.输入账号和密码
        Scanner scanner = new Scanner(System.in);
        String account = scanner.nextLine();
        String password = scanner.nextLine();
        //scanner.close();

        // 2.JDBC的使用
        // 2.1 创建驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.2 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jdbc-test?user=root&password=HJW413608");

        // 2.3 获取statement
        Statement statement = connection.createStatement();

        // 2.4 查询用户，创建SQL语句
        String sql = "SELECT id, account, password, nickname FROM t_user WHERE account = '" + account + "' AND password = '" + password + "';";
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            System.out.println("登录成功！");
        } else {
            System.out.println("登录失败！");
        }

        // 2.5 关闭资源
        resultSet.close();
        statement.close();
        connection.close();
        scanner.close();

    }
}
