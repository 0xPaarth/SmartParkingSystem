package com.parking.domain.export;

import java.io.OutputStream;
import java.util.Collection;

public interface DataExporter { void export(Collection<?> data, OutputStream output); }
