package com.karka_deh.repos;

import com.karka_deh.models.db.Column;
import com.karka_deh.models.db.StandaloneConstraint;
import com.karka_deh.models.db.TableElement;
import com.karka_deh.models.entities.PostEntity;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postRepo")
@DependsOn("userRepo")
public class PostRepo extends BaseRepo<PostEntity> {
  private final JdbcTemplate jdbc;

  public PostRepo(JdbcTemplate jdbc) {
    super(jdbc, PostEntity.class, "posts");
    this.jdbc = jdbc;
  }

  @Override
  protected List<TableElement> getTableStructure() {
    return List.of(
        new Column("id", "UUID", "PRIMARY KEY", "DEFAULT", "gen_random_uuid()"),
        new Column("author_id", "UUID", "NOT NULL"),
        new Column("title", "VARCHAR(255)", "NOT NULL"),
        new Column("content", "TEXT", "NOT NULL"),
        new Column("created_at", "TIMESTAMP WITH TIME ZONE", "DEFAULT", "CURRENT_TIMESTAMP"),
        new StandaloneConstraint("CONSTRAINT fk_posts_author FOREIGN KEY (author_id) REFERENCES users(id)"));

  }

  public Optional<PostEntity> findBySlug(String slug) {
    String sql = "SELECT * FROM posts WHERE slug = ?";
    return this.jdbc.query(sql, new BeanPropertyRowMapper<>(PostEntity.class), slug).stream().findFirst();
  }

  public List<PostEntity> findAllPostsByUserId(UUID id) {
    String sql = "SELECT * FROM posts WHERE author_id = ?";
    return this.jdbc.query(sql, new BeanPropertyRowMapper<>(PostEntity.class), id);
  }

  public List<PostEntity> findAllPostsByUserId(UUID id, int page, int size) {
    int offset = size * page;

    String sql = """
        SELECT * FROM posts WHERE author_id = ?
        ORDER BY created_at
        LIMIT ? OFFSET ?
        """;
    return this.jdbc.query(sql, new BeanPropertyRowMapper<>(PostEntity.class), id, size, offset);
  }

  public List<PostEntity> searchPosts(String keyword, int page, int size) {
    int offset = size * page;

    String sql = """
          SELECT * FROM posts
          WHERE LOWER(title) LIKE ? OR LOWER(content) LIKE ?
          ORDER BY created_at DESC
          LIMIT ? OFFSET ?

        """;

    return List.of();
  }

  public int countPostsByUserId(UUID userId) {
    String sql = "SELECT COUNT(*) FROM posts WHERE author_id = ?";
    Integer total = jdbc.queryForObject(sql, Integer.class, userId);
    return total != null ? total : 0;
  }

  @Override
  protected PreparedStatement getInsertPreparedStatementSetter(Connection conn, String sql, PostEntity entity)
      throws SQLException {

    Timestamp timestamp = entity.getCreatedAt() == null
        ? Timestamp.valueOf(LocalDateTime.now())
        : Timestamp.valueOf(entity.getCreatedAt());

    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setObject(1, entity.getId(), java.sql.Types.OTHER);
    ps.setObject(2, entity.getAuthorId(), java.sql.Types.OTHER);
    ps.setString(3, entity.getTitle());
    ps.setString(4, entity.getContent());
    ps.setTimestamp(5, timestamp);
    return ps;
  }

}
