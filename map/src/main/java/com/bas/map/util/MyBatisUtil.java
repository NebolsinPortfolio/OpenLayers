package com.bas.map.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utils for MyBatis
 */
public class MyBatisUtil {

    private static SqlSessionFactory sessionFactory;

    public static SqlSessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException ex) {
                new RuntimeException(ex.getMessage());
            }
        }
        return sessionFactory;
    }

    public static SqlSession openSession() {
        return sessionFactory.openSession();
    }
}
