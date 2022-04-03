package co.copper.testtask.clients;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RestHelper {

  public static <T> Long getResourceId(Response<T> response) {
    final URI resourceLocation;
    try {
      resourceLocation = new URI(Objects.requireNonNull(getLocationHeader(response)));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    return Long.valueOf(new File(resourceLocation.getPath()).getName());
  }

  public static <T> String getLocationHeader(Response<T> response) {
    return response.headers().get("Location");
  }

  public static void verifyResponseStatusCreated(Response response) {
    verifyResponseStatus(response, HttpStatus.SC_CREATED);
  }

  public static void verifyResponseStatus(Response response, int statusCode) {
    try {
      assertThat(response.code())
          .as(
              "HTTP response returned with '%s' message",
              response.errorBody() != null ? response.errorBody().string() : null)
          .isEqualTo(statusCode);
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
  }
}
