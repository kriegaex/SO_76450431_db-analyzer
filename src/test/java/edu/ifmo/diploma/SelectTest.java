package edu.ifmo.diploma;

import org.junit.jupiter.api.Test;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SelectTest {
    Logger log = LoggerFactory.getLogger(SelectTest.class);

    @Test
    public void selectTest() throws SQLException, InterruptedException {
        DriverManager.registerDriver(new Driver());
        Properties properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "pwd");
        try (
                var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", properties);
                var statement = connection.createStatement();
                var resultSet = statement.executeQuery("SELECT * FROM example");
        ) {
            while (resultSet.next()) {
                log.debug(resultSet.getString(1));
            }
            Thread.sleep(10000);
        }
    }
}
