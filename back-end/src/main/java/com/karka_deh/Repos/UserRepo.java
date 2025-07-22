
package com.karka_deh.Repos;

import com.karka_deh.Models.User;
import com.karka_deh.Models.DB.Column;
import com.karka_deh.Models.DB.TableElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("userRepo")
public class UserRepo extends BaseRepo<User> {

  public UserRepo(JdbcTemplate jdbc) {
    super(jdbc, User.class, "users");
  }

  @Override
  protected PreparedStatement getInsertPreparedStatementSetter(Connection conn, String sql, User entity)
      throws SQLException {

    Timestamp timestamp = entity.getCreatedAt() == null
        ? Timestamp.valueOf(LocalDateTime.now())
        : Timestamp.valueOf(entity.getCreatedAt());

    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, entity.getId().toString());
    ps.setString(2, entity.getUsername());
    ps.setString(3, entity.getEmail());
    ps.setString(4, entity.getPasswordHash());
    ps.setTimestamp(5, timestamp);
    return ps;
  }

  @Override
  protected List<TableElement> getTableStructure() {
    return List.of(
        new Column("id", "VARCHAR2(36)", "NOT", "NULL", "PRIMARY", "KEY"),
        new Column("username", "VARCHAR2(64)", "UNIQUE", "NOT", "NULL"),
        new Column("email", "VARCHAR2(128)", "UNIQUE", "NOT", "NULL"),
        new Column("password_hash", "VARCHAR2(255)", "NOT", "NULL"),
        new Column("created_at", "TIMESTAMP", "DEFAULT", "CURRENT_TIMESTAMP"));
  }

}
