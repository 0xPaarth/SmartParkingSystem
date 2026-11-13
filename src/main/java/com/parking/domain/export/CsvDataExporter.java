package com.parking.domain.export;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public final class CsvDataExporter implements DataExporter {
  @Override
  public void export(Collection<?> data, OutputStream output) {
    try {
      output.write("value\n".getBytes(StandardCharsets.UTF_8));
      for (Object value : data) {
        String row = value == null ? "" : value.toString();
        output.write((escape(row) + "\n").getBytes(StandardCharsets.UTF_8));
      }
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to export CSV", exception);
    }
  }

  private String escape(String value) {
    return "\"" + value.replace("\"", "\"\"").replace("\n", " ") + "\"";
  }
}
