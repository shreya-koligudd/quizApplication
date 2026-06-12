package javaQuiz;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;

/**
 *
 * @author Shreya
 */
public class DBConnection {

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            String URL = props.getProperty("db.url");
            String USER = props.getProperty("db.user");
            String PASSWORD = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {
            System.out.println("DB Connection Failed: " + e.getMessage());
            return null;
        }
    }
}