package org.prep.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
  static final ObjectMapper mapper = new ObjectMapper();

  static String prettyPrint(final String input) {
    try {
      Object jsonObject = mapper.readValue(input, Object.class);
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  static String prettyPrintObject(final Object input) {
    try {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

}
