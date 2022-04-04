package co.copper.testtask.state;

import co.copper.testtask.dto.AssetDto;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An instance of this class will be injected in each scenario, thus allowing all scenarios to be
 * run in parallel.
 */
@Getter
@Setter
public class DataHolder {

  private static final DataHolder INSTANCE = new DataHolder();
  private Map<String, Supplier<String>> testDataMap = new HashMap<>();
  private Response response;
  private List<AssetDto> collateralDtos = Collections.synchronizedList(new ArrayList<>());

  public static DataHolder getInstance() {
    return INSTANCE;
  }
}
