package org.prep.example.model;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    Properties connectionParams;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "driver";
    private static final String HOST_STRING = "host";
    private static final int LOGIN_TIMEOUT = 10;

    public DBConnector(String fileName) {
        connectionParams = readFile(fileName);
        if (connectionParams == null) {
            throw new RuntimeException("Had problems loading from file: " + fileName);
        }
    }

    private Properties readFile(String fileName) {
        try {
            Properties toRead = new Properties();
            toRead.load(new FileReader(fileName));
            return toRead;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    public Connection connectToDB() throws ClassNotFoundException, SQLException {
        String host = connectionParams.getProperty(HOST_STRING).toString();
        String username = connectionParams.getProperty(USERNAME).toString();
        String password = connectionParams.getProperty(PASSWORD).toString();
        String driver = connectionParams.getProperty(DRIVER).toString();

        System.out.println("host: " + host + "\nusername: " + username + "\npassword: " + password + "\ndriver: " + driver);

        Class.forName(driver);
        System.out.println("--------------------------");
        System.out.println("DRIVER: " + driver);
        System.out.println("Set Login Timeout: " + LOGIN_TIMEOUT);
        DriverManager.setLoginTimeout(LOGIN_TIMEOUT);
        Connection connection = DriverManager.getConnection(host, username, password);
        System.out.println("CONNECTION: " + connection);

        return connection;
    }
}
