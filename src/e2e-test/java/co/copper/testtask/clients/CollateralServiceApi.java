package co.copper.testtask.clients;

import co.copper.testtask.dto.CollateralDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CollateralServiceApi {

  @POST("/api/v1/collaterals")
  Call<Void> createCollateral(@Body CollateralDto dto);

  @GET("/api/v1/collaterals/{id}")
  Call<CollateralDto> getCollateralById(@Path("id") Long id);
}
