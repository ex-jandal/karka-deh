package com.karka_deh.repos;

import com.karka_deh.models.reqs.Post;
import com.karka_deh.models.db.Column;
import com.karka_deh.models.db.StandaloneConstraint;
import com.karka_deh.models.db.TableElement;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@DependsOn("userRepo")
public class PostRepo extends BaseRepo<Post> {
  private final JdbcTemplate jdbc;

  public PostRepo(JdbcTemplate jdbc) {
    super(jdbc, Post.class, "posts");
    this.jdbc = jdbc;
  }

  @Override
  protected List<TableElement> getTableStructure() {

    return List.of(
        new Column("id", "VARCHAR2(36)", "NOT", "NULL", "PRIMARY", "KEY"),
        new Column("author_id", "VARCHAR2(36)", "NOT", "NULL"),
        new Column("title", "VARCHAR2(255)", "NOT", "NULL"),
        new Column("content", "CLOB", "NOT", "NULL"),
        new Column("created_at", "TIMESTAMP", "DEFAULT", "CURRENT_TIMESTAMP"),
        new StandaloneConstraint("CONSTRAINT fk_posts_author FOREIGN KEY (author_id) REFERENCES users(id)"));

  }

  public Optional<Post> findByTitle(String title) {
    String sql = "SELECT * FROM posts WHERE title = ?";
    return this.jdbc.query(sql, new BeanPropertyRowMapper<>(Post.class), title).stream().findFirst();
  }

  @Override
  protected PreparedStatement getInsertPreparedStatementSetter(Connection conn, String sql, Post entity)
      throws SQLException {

    Timestamp timestamp = entity.getCreatedAt() == null
        ? Timestamp.valueOf(LocalDateTime.now())
        : Timestamp.valueOf(entity.getCreatedAt());

    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, entity.getId().toString());
    ps.setString(2, entity.getAuthorId().toString());
    ps.setString(3, entity.getTitle());
    ps.setCharacterStream(4, new StringReader(entity.getContent()), entity.getContent().length());
    ps.setTimestamp(5, timestamp);
    return ps;
  }

}
