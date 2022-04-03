package co.copper.testtask.steps.rest;

import co.copper.testtask.clients.CollateralServiceClient;
import co.copper.testtask.clients.RestHelper;
import co.copper.testtask.config.ApplicationProperties;
import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.state.DataHolder;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;

import static co.copper.testtask.clients.RestHelper.verifyResponseStatusCreated;
import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.COLLATERAL_SERVICE_HOST;
import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.COLLATERAL_SERVICE_PORT;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ScenarioScoped
public class RestServiceSteps {

  @Inject private DataHolder dataHolder;
  @Inject private CollateralServiceClient collateralServiceClient;
  //  @Inject private EnvironmentConfig config;

  @When("^REST - create new Collateral - verify response = (true|false)$")
  public void restCreateNewCollateralVerifyResponseTrue(
      boolean verifyResponse, List<AssetDto> collateralDtos) {

    collateralDtos.forEach(
        collateralDto -> {
          final var response =
              collateralServiceClient.invoke(api -> api.createCollateral(collateralDto));

          if (verifyResponse) {
            verifyResponseStatusCreated(response);
            String locationHeader = RestHelper.getLocationHeader(response);

            assertThat(locationHeader)
                .matches(
                    String.format(
                        "http://%s:%s%s",
                        ApplicationProperties.getString(COLLATERAL_SERVICE_HOST),
                        ApplicationProperties.getInteger(COLLATERAL_SERVICE_PORT),
                        "/api/v1/collaterals/[0-9]+"));
            final Long resourceId = RestHelper.getResourceId(response);
            collateralDto.setId(resourceId);
            log.info("REST - created Collateral with id: {}", resourceId);
          } else {
            dataHolder.setResponse(response);
          }
        });
    dataHolder.setCollateralDtos(collateralDtos);
  }

  @Then("^REST - get Collaterals$")
  public void restGetCollateral(List<AssetDto> expectedCollateralDtos) {

    expectedCollateralDtos.forEach(
        expectedCollateralDto -> {
          final var actualCollateralResponseDto =
              collateralServiceClient
                  .invoke(api -> api.getCollateralById(expectedCollateralDto.getId()))
                  .body();

          assertThat(actualCollateralResponseDto)
              .usingRecursiveComparison()
              .isEqualTo(expectedCollateralDto);
        });
  }
}
