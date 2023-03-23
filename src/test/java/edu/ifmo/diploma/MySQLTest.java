package edu.ifmo.diploma;

import edu.ifmo.diploma.context.AnalyzerContext;
import edu.ifmo.diploma.proccessor.FullScanProcessor;
import edu.ifmo.diploma.proccessor.ManyJoinsProcessor;
import io.vavr.control.Try;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
import static org.testcontainers.shaded.org.hamcrest.Matchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.Matchers.lessThan;

@Tag("MySQL")
public class MySQLTest extends IntegrationTest {
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("mysql")
            .withUsername("mysql")
            .withPassword("mysql")
            .withInitScript("init-mysql.sql");

    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", mySQLContainer.getUsername());
        properties.setProperty("password", mySQLContainer.getPassword());
        connection = DriverManager.getConnection(mySQLContainer.getJdbcUrl(), properties);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    public void selectAnalyze() throws SQLException {
        AtomicInteger reportCount = new AtomicInteger();
        StringBuilder stringBuilder = new StringBuilder();
        AnalyzerContext.getInstance(content -> {
                    Try.run(() -> writeToFile(content))
                            .orElseRun(Throwable::printStackTrace);
                    reportCount.incrementAndGet();
                    stringBuilder.append(content);
                }, Collections.emptyList()
        );
        try (
                var statement = connection.createStatement();
        ) {
            statement.execute("SELECT * FROM bar");
            await().atMost(Duration.ofSeconds(flushPeriod())).untilAtomic(reportCount, equalTo(1));
            assertThat(stringBuilder.toString()).contains("Advice type - " + FullScanProcessor.PROCESSOR_TYPE);
        }
    }

    @Test
    public void selectWithoutAnalyze() throws SQLException {
        AtomicInteger reportCount = new AtomicInteger();
        AnalyzerContext.getInstance(content -> reportCount.incrementAndGet(), Collections.emptyList());
        try (
                var statement = connection.createStatement();
        ) {
            statement.execute("SELECT id FROM bar");
            await().atMost(Duration.ofSeconds(flushPeriod())).untilAtomic(reportCount, lessThan(1));
        }
    }
}
