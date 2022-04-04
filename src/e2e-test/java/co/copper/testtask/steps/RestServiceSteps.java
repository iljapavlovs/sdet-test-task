package co.copper.testtask.steps;

import co.copper.testtask.clients.CollateralServiceClient;
import co.copper.testtask.config.ApplicationProperties;
import co.copper.testtask.dto.AssetDto;
import co.copper.testtask.dto.CollateralDto;
import co.copper.testtask.state.DataHolder;
import co.copper.testtask.utils.RestHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import retrofit2.Response;

import java.util.List;

import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.COLLATERAL_SERVICE_HOST;
import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.COLLATERAL_SERVICE_PORT;
import static co.copper.testtask.utils.RestHelper.verifyResponseStatusCreated;
import static co.copper.testtask.utils.RestHelper.verifyResponseStatusOk;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RestServiceSteps {

  private final DataHolder dataHolder = DataHolder.getInstance();
  private CollateralServiceClient collateralServiceClient;

  @When("^REST - create new Collateral - verify response = (true|false)$")
  public void restCreateNewCollateralVerifyResponseTrue(
      boolean verifyResponse, List<AssetDto> collateralDtos) {
    this.collateralServiceClient = new CollateralServiceClient();
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

    final var actualCollateralDtos = dataHolder.getCollateralDtos();

    expectedCollateralDtos.forEach(
        expectedCollateralDto -> {
          final var actualDto =
              actualCollateralDtos.stream()
                  .filter(
                      expectedDto -> expectedDto.getName().equals(expectedCollateralDto.getName()))
                  .findFirst()
                  .orElseThrow(
                      () ->
                          new AssertionError(
                              String.format(
                                  "Collateral with name - %s not found",
                                  expectedCollateralDto.getName())));

          Response<CollateralDto> response =
              collateralServiceClient.invoke(api -> api.getCollateralById(actualDto.getId()));

          verifyResponseStatusOk(response);
          final var actualCollateralResponseDto = response.body();

          assertThat(actualCollateralResponseDto)
              .usingRecursiveComparison()
              .ignoringFields("id")
              .isEqualTo(expectedCollateralDto);
        });
  }

  @Then("^Response - HTTP Status - (BAD REQUEST|OK|CREATED|INTERNAL SERVER ERROR)$")
  public void responseHTTPStatus(String expectedHttpStatus) {
    final var actualResponse = dataHolder.getResponse();
    final var httpStatusCode = getHttpStatusCodeByName(expectedHttpStatus);
    RestHelper.verifyResponseStatus(actualResponse, httpStatusCode);
  }

  private int getHttpStatusCodeByName(String expectedHttpStatus) {
    int httpStatusCode;
    switch (expectedHttpStatus) {
      case "BAD REQUEST":
        httpStatusCode = HttpStatus.BAD_REQUEST.value();
        break;
      case "OK":
        httpStatusCode = HttpStatus.OK.value();
        break;
      case "INTERNAL SERVER ERROR":
        httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        break;
      case "CREATED":
        httpStatusCode = HttpStatus.CREATED.value();
        break;
      default:
        throw new IllegalArgumentException("Unknown parameter: " + expectedHttpStatus);
    }
    return httpStatusCode;
  }
}
