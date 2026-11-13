package com.parking.domain.export;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public final class PdfDataExporter implements DataExporter {
  @Override
  public void export(Collection<?> data, OutputStream output) {
    StringBuilder body = new StringBuilder("Parking report\n\n");
    data.forEach(value -> body.append(value == null ? "" : value).append('\n'));
    byte[] content = body.toString().replace("(", "[").replace(")", "]")
        .getBytes(StandardCharsets.UTF_8);
    String header = "%PDF-1.4\n1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n"
        + "2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n"
        + "3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] "
        + "/Contents 4 0 R >>\nendobj\n4 0 obj\n<< /Length " + content.length
        + " >>\nstream\nBT /F1 10 Tf 40 740 Td (";
    try {
      output.write(header.getBytes(StandardCharsets.UTF_8));
      output.write(content);
      output.write(") Tj ET\nendstream\nendobj\ntrailer\n<< /Root 1 0 R >>\n%%EOF".getBytes(StandardCharsets.UTF_8));
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to export PDF", exception);
    }
  }
}
