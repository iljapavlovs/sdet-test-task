package co.copper.testtask.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Properties;

import static co.copper.testtask.config.ApplicationProperties.ApplicationProperty.APP_ENV;

public class ApplicationProperties {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);
  private static final HashMap<String, Properties> DEFAULT_VALUES =
      new HashMap<>() {
        {
          put(
              "default",
              new Properties() {
                {
                  setProperty(ApplicationProperty.COLLATERAL_SERVICE_HOST.name, "localhost");
                  setProperty(ApplicationProperty.COLLATERAL_SERVICE_PORT.name, "8080");
                }
              });

          put(
              "local",
              new Properties() {
                {
                  setProperty(ApplicationProperty.COLLATERAL_SERVICE_HOST.name, "localhost");
                  setProperty(ApplicationProperty.COLLATERAL_SERVICE_PORT.name, "8080");
                }
              });
        }
      };

  private static String getString(String propertyName) {
    String currentEnv =
        System.getProperties()
            .getProperty(APP_ENV.name, System.getenv(APP_ENV.name.toUpperCase().replace('.', '_')));

    if (System.getProperties().containsKey(propertyName)) {
      return System.getProperties().getProperty(propertyName);
    }
    if (currentEnv != null) {
      if (DEFAULT_VALUES.get(currentEnv).containsKey(propertyName)) {
        return DEFAULT_VALUES.get(currentEnv).getProperty(propertyName);
      }
    }
    if (DEFAULT_VALUES.get("default").containsKey(propertyName)) {
      return DEFAULT_VALUES.get("default").getProperty(propertyName);
    }

    logger.warn("Unknown application property: " + propertyName);
    throw new RuntimeException("Unknown application property: " + propertyName);
  }

  public static String getString(ApplicationProperty property) {
    return getString(property.name);
  }

  public static Integer getInteger(ApplicationProperty property) {
    return Integer.valueOf(getString(property));
  }

  public static boolean getBoolean(ApplicationProperty property) {
    return Boolean.parseBoolean(getString(property));
  }

  public enum ApplicationProperty {
    APP_ENV("application.env"),
    COLLATERAL_SERVICE_HOST("collateral-service.host"),
    COLLATERAL_SERVICE_PORT("collateral-service.port");

    private final String name;

    ApplicationProperty(String name) {
      this.name = name;
    }
  }
}
