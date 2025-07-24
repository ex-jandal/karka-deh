package com.karka_deh.models.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Column implements TableElement {
  private final String name;
  private final String dataType;
  private final List<String> constraints;

  public Column(String name, String dataType, String... constraints) {
    this.name = name;
    this.dataType = dataType;
    this.constraints = new ArrayList<>(Arrays.asList(constraints));
  }

  public String getName() {
    return name;
  }

  public String getDataType() {
    return dataType;
  }

  public List<String> getConstraints() {
    return constraints;
  }

  @Override
  public String toSQL() {
    StringBuilder sb = new StringBuilder();
    sb.append(name).append(" ").append(dataType);
    if (!constraints.isEmpty()) {
      sb.append(" ").append(String.join(" ", constraints));
    }
    return sb.toString();
  }

  @Override
  public Optional<String> filedName() {
    return Optional.of(this.name);
  }

}
