package org.prep.example;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.prep.example.model.DBConnector;

import static org.jooq.impl.DSL.constraint;

public class DBConnectionTest {

    public static final String CURRENT_DATE = "current_date";

    public static void main(String[] args) {
        if (args.length > 0) {
            DBConnector dbConnector = new DBConnector(args[0]);
            try {
                Connection connection = dbConnector.connectToDB();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select " + CURRENT_DATE);
                if (resultSet != null && !resultSet.isClosed()) {
                    while (resultSet.next()) {
                        String data = resultSet.getString(CURRENT_DATE);
                        System.out.println("date: " + data);
                    }
                } else {
                    System.out.println("result set is closed");
                }
                runDSL(connection);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void runDSL(Connection connection) {
        DSLContext context = DSL.using(connection, SQLDialect.MARIADB);
        int value = context.createTableIfNotExists("country")
                .column("name", SQLDataType.VARCHAR(50))
                .constraints(
                        constraint("UK_COUNTRY_NAME").unique("name")
                )
                .execute();

        System.out.println("value from create statement: " + value);
    }
}
