package com.karka_deh.Repos;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.karka_deh.Models.DB.TableElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseRepo<T> {
  protected final JdbcTemplate jdbc;
  private final Class<T> entityClass;
  private final String tableName;

  protected BaseRepo(JdbcTemplate jdbc, Class<T> entityClass, String tableName) {
    this.jdbc = jdbc;
    this.entityClass = entityClass;
    this.tableName = tableName;
    this.ensureTableExists();
  }

  /**
   * Returns the table structure as a mapping of column names to their
   * definitions.
   *
   * Each key in the map is a column name (field),
   * and the corresponding value is a list of strings representing
   * the data type and associated constraints for that column.
   *
   * Example:
   * {
   * "username": ["VARCHAR2(64)", "UNIQUE", "NOT NULL"]
   * }
   *
   * Note: Constraints and data types are represented as separate strings in the
   * list.
   *
   * @return Map where keys are column names and values are lists of data types
   *         and constraints.
   */
  protected abstract List<TableElement> getTableStructure();

  protected abstract PreparedStatement getInsertPreparedStatementSetter(Connection conn, String sql, T entity)
      throws SQLException;

  private void ensureTableExists() {
    // oracle store the table names in uppercase, weird!
    String checkSql = "SELECT COUNT(*) FROM user_tables WHERE table_name = '" + this.tableName.toUpperCase() + "'";

    Integer count = this.jdbc.queryForObject(
        checkSql,
        Integer.class);

    if (count != null && count == 0) {

      StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE " + this.tableName + " (");

      final List<TableElement> tableStructure = this.getTableStructure();

      int size = tableStructure.size();

      for (int i = 0; i < size; i++) {
        sqlBuilder.append("  ").append(tableStructure.get(i).toSQL());
        if (i < size - 1) {
          sqlBuilder.append(",");
        }
      }

      sqlBuilder.append(")");

      System.out.println(sqlBuilder);
      this.jdbc.execute(sqlBuilder.toString());
      System.out.println("✅ Created table '" + this.tableName + "'");
    }
  }

  public List<T> findAll() {

    // ORACLE returns the fields in uppercase
    //
    // but `BeanPropertyRowMapper` needs a somewhat relatable fields to bind them to
    // it, so you either select them as all uppercase, like so:
    // 'AUTHORID' (unlike ORACLE which return like 'AUTHOR_ID') which confuses the
    // BeanPropertyRowMapper
    //
    // or you include them in a quotation so it will rename them exactly like that
    //
    // and you can also do author_id as "authorId" because we already have an
    // `@JsonAlias` in the `Post` class
    //
    // TODO: this is a janky solution, we should probably fix it
    //

    String sql = "SELECT " +
        this.getTableStructure().stream()
            .map(TableElement::filedName)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(key -> String.format("%s AS \"%s\"", key, key)) // quoting for oracle
            .collect(Collectors.joining(", "))
        +
        " FROM " + this.tableName;

    System.out.println(sql);

    return jdbc.query(sql, new BeanPropertyRowMapper<T>(entityClass));
  }

  public Optional<T> findById(UUID id) {
    String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
    List<T> results = jdbc.query(sql, new BeanPropertyRowMapper<>(entityClass), id);
    return results.stream().findFirst();
  }

  public void save(T entity) {
    StringBuilder sqlBuilder = new StringBuilder("INSERT INTO " + this.tableName + " (");

    List<TableElement> tableStructure = this.getTableStructure();

    // only include named fields (Columns, not constraints)
    List<String> fieldNames = tableStructure.stream()
        .map(TableElement::filedName)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();

    sqlBuilder.append(String.join(", ", fieldNames));
    sqlBuilder.append(") VALUES (");

    sqlBuilder.append(String.join(", ", Collections.nCopies(fieldNames.size(), "?")));
    sqlBuilder.append(")");

    String sql = sqlBuilder.toString();
    System.out.println(sql);

    this.jdbc.update(conn -> this.getInsertPreparedStatementSetter(conn, sql, entity));
  }

}
