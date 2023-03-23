package edu.ifmo.diploma;

import edu.ifmo.diploma.context.AnalyzerContext;
import edu.ifmo.diploma.proccessor.FullScanProcessor;
import edu.ifmo.diploma.proccessor.SharedBuffersProcessor;
import io.vavr.control.Try;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.postgresql.Driver;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
import static org.testcontainers.shaded.org.hamcrest.Matchers.equalTo;
import static org.testcontainers.shaded.org.hamcrest.Matchers.lessThan;

@Tag("PostgreSQL")
public class PostgreSQLTest extends IntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:14.7")
    )
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withCommand("-c shared_buffers=32MB")
            .withInitScript("init-pg.sql");

    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        DriverManager.registerDriver(new Driver());
        Properties properties = new Properties();
        properties.setProperty("user", postgresqlContainer.getUsername());
        properties.setProperty("password", postgresqlContainer.getPassword());
        connection = DriverManager.getConnection(postgresqlContainer.getJdbcUrl(), properties);
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
            statement.execute("SELECT * FROM foo");
            await().atMost(Duration.ofSeconds(flushPeriod())).untilAtomic(reportCount, equalTo(1));
            assertThat(stringBuilder.toString()).contains(
                    "Advice type - " + FullScanProcessor.PROCESSOR_TYPE,
                    "Advice type - " + SharedBuffersProcessor.PROCESSOR_TYPE
            );
        }
    }

    @Test
    public void selectWithoutAnalyze() throws SQLException {
        AtomicInteger reportCount = new AtomicInteger();
        AnalyzerContext.getInstance(content -> reportCount.incrementAndGet(), Collections.emptyList());
        try (
                var statement = connection.createStatement();
        ) {
            statement.execute("SELECT c1 FROM foo");
            await().atMost(Duration.ofSeconds(flushPeriod())).untilAtomic(reportCount, lessThan(1));
        }
    }
}
