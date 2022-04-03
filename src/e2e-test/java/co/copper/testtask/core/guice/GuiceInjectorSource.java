package co.copper.testtask.core.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;
import io.cucumber.guice.CucumberModules;
import io.cucumber.guice.InjectorSource;

public class GuiceInjectorSource implements InjectorSource {

  @Override
  public Injector getInjector() {
    return Guice.createInjector(
        Stage.PRODUCTION,
        CucumberModules.createScenarioModule(),
        new CloseableModule(),
        new Jsr250Module());
  }
}
