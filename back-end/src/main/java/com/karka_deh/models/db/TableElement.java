package com.karka_deh.models.db;

import java.util.Optional;

public interface TableElement {
  String toSQL();

  Optional<String> filedName();
}
