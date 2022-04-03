package co.copper.testtask.state;

import co.copper.testtask.dto.AssetDto;
import io.cucumber.guice.ScenarioScoped;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An instance of this class will be injected in each scenario, thus allowing all scenarios to be
 * run in parallel.
 */
@ScenarioScoped
@Getter
@Setter
public class DataHolder {
  private Response response;
  private List<AssetDto> collateralDtos = Collections.synchronizedList(new ArrayList<>());
}
