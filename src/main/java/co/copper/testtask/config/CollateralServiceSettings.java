package co.copper.testtask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application")
public class CollateralServiceSettings {
  private String url;

  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }

}
