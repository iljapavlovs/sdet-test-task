package co.copper.testtask.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;

import java.lang.reflect.Type;

/**
 * https://cucumber.io/docs/cucumber/configuration/
 * We can define our own mechanism for Data Table transformation. In 1.2.5 version this was done automagically
 *
 * should on glue path (where steps are defined)
 */
public class ParameterTypes {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @DefaultParameterTransformer
  @DefaultDataTableEntryTransformer
  @DefaultDataTableCellTransformer
  public Object transformer(Object fromValue, Type toValueType) {
    return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
  }

}