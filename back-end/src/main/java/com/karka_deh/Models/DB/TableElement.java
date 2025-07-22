package com.karka_deh.Models.DB;

import java.util.Optional;

public interface TableElement {
  String toSQL();

  Optional<String> filedName();
}
