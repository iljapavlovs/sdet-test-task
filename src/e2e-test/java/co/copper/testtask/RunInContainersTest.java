package co.copper.testtask;

import co.copper.testtask.config.ApplicationProperties;
import co.copper.testtask.utils.ServiceEndpoint;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.COLLATERAL_SERVICE_PORT;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Slf4j
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("co/copper/testtask")
@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "progress")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "summary")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "co.copper.testtask")
public class RunInContainersTest {

    public static final ServiceEndpoint SERVICE_INTERNAL_ENDPOINT =
        new ServiceEndpoint(
            "collateral-service", ApplicationProperties.getInteger(COLLATERAL_SERVICE_PORT));

    @SuppressWarnings("unchecked")
    public static final DockerComposeContainer ECOSYSTEM =
            new DockerComposeContainer<>(new File("docker-compose.yml"))
                    .withExposedService(
                            SERVICE_INTERNAL_ENDPOINT.getHost(),
                            1,
                            SERVICE_INTERNAL_ENDPOINT.getPort(),
                            Wait.forHttp("/actuator/health")
                                    .forStatusCode(200)
                                    .withStartupTimeout(Duration.ofMinutes(1)))
                    .withLocalCompose(true)
                    .withPull(true)
                    .withTailChildContainers(true)
                    .withLogConsumer(SERVICE_INTERNAL_ENDPOINT.getHost(), new
                            Slf4jLogConsumer(log));
    @BeforeAll
    public static void beforeAll() {
      ECOSYSTEM.start();
    }

    @AfterAll
    public static void afterAll() {
      ECOSYSTEM.stop();
    }

}
