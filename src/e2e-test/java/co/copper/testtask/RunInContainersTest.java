package co.copper.testtask;

import co.copper.testtask.config.ApplicationProperties;
import co.copper.testtask.utils.ServiceEndpoint;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.COLLATERAL_SERVICE_PORT;

@Slf4j
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm", "summary", "pretty"},
    features = {"src/e2e-test/resources/features"},
    tags = "@docker",
    glue = {"co.copper.testtask.steps"})
public class RunInContainersTest {

  public static final ServiceEndpoint SERVICE_INTERNAL_ENDPOINT =
      new ServiceEndpoint(
          "collateral-service", ApplicationProperties.getInteger(COLLATERAL_SERVICE_PORT));

  @SuppressWarnings("unchecked")
  @ClassRule
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
          .withLogConsumer(SERVICE_INTERNAL_ENDPOINT.getHost(), new Slf4jLogConsumer(log));
}
