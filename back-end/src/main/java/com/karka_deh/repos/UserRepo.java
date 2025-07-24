
package com.karka_deh.repos;

import com.karka_deh.models.reqs.User;
import com.karka_deh.models.db.Column;
import com.karka_deh.models.db.TableElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("userRepo")
public class UserRepo extends BaseRepo<User> {

  public UserRepo(JdbcTemplate jdbc) {
    super(jdbc, User.class, "users");
  }

  public boolean existsByUsername(String username) {
    String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
    Integer count = super.jdbc.queryForObject(sql, Integer.class, username);

    return count == 1;
  }

  public void createUser(String username, String passwordHash) {
    UUID uuid = UUID.randomUUID();
    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    String email = username + "@karka-deh.com";

    String sql = "INSERT INTO users (id, username, email, password_hash, created_at) VALUES (?, ?, ?, ?, ?)";

    super.jdbc.update(sql, uuid.toString(), username, email, passwordHash, timestamp);
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
