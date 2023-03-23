package edu.ifmo.diploma;

import edu.ifmo.diploma.context.AnalyzerContext;
import edu.ifmo.diploma.proccessor.ManyJoinsProcessor;
import io.vavr.control.Try;
import oracle.jdbc.OracleDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.OracleContainer;
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

@Tag("Oracle")
public class OracleTest extends IntegrationTest {
    @Container
    private static final OracleContainer oracle = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:21-slim"))
            .withDatabaseName("oracle")
            .withUsername("oracle")
            .withPassword("oracle")
            .withInitScript("init-oracle.sql");

    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        Properties properties = new Properties();
        properties.setProperty("user", oracle.getUsername());
        properties.setProperty("password", oracle.getPassword());
        connection = DriverManager.getConnection(oracle.getJdbcUrl(), properties);
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
            assertThat(stringBuilder.toString()).doesNotContain("Advice type - " + ManyJoinsProcessor.PROCESSOR_TYPE);        }
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
