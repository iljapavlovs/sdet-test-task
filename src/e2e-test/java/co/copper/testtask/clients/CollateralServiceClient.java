package co.copper.testtask.clients;

import co.copper.testtask.config.ApplicationProperties;
import co.copper.testtask.utils.ServiceEndpoint;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.guice.ScenarioScoped;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.function.Function;

import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.COLLATERAL_SERVICE_HOST;
import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.COLLATERAL_SERVICE_PORT;

@Slf4j
@ScenarioScoped
public class CollateralServiceClient {

  private CollateralServiceApi collateralServiceApi;

  @Inject
  public CollateralServiceClient() {
    createCollateralServiceApi();
  }

  private void createCollateralServiceApi() {
    ServiceEndpoint serviceEndpoint =
        new ServiceEndpoint(
            ApplicationProperties.getString(COLLATERAL_SERVICE_HOST),
            ApplicationProperties.getInteger(COLLATERAL_SERVICE_PORT));
    ObjectMapper mapper =
        new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    this.collateralServiceApi =
        new Retrofit.Builder()
            .baseUrl(serviceEndpoint.getBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .client(new OkHttpClient.Builder().addInterceptor(new AllureOkHttp3()).build())
            .build()
            .create(CollateralServiceApi.class);
  }

  public <T> Response<T> invoke(Function<CollateralServiceApi, Call<T>> restCall) {
    try {
      return restCall.apply(collateralServiceApi).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
