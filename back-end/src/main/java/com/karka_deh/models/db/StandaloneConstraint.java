package com.karka_deh.models.db;

import java.util.Optional;

public class StandaloneConstraint implements TableElement {
  private final String constraintSQL;

  public StandaloneConstraint(String constraintSQL) {
    this.constraintSQL = constraintSQL;
  }

  @Override
  public String toSQL() {
    return constraintSQL;
  }

  @Override
  public Optional<String> filedName() {
    return Optional.empty();
  }
}
