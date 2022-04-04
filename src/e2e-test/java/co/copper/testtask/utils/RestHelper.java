package co.copper.testtask.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    verifyResponseStatus(response, HttpStatus.CREATED.value());
  }

  public static void verifyResponseStatusOk(Response response) {
    verifyResponseStatus(response, HttpStatus.OK.value());
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

  public static int getHttpStatusCodeByName(String expectedHttpStatus) {
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
