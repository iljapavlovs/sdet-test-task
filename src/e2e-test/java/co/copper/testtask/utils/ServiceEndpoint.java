package co.copper.testtask.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static java.lang.String.format;

@AllArgsConstructor
@Getter
@Setter
public class ServiceEndpoint {

  private final String host;
  private final int port;

  public String getBaseUrl() {
    return format("%s://%s:%s", "http", getHost(), getPort());
  }
}
