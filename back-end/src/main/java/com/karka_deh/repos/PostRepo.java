package com.karka_deh.repos;

import com.karka_deh.models.db.Column;
import com.karka_deh.models.db.StandaloneConstraint;
import com.karka_deh.models.db.TableElement;
import com.karka_deh.models.entities.PostEntity;
import com.karka_deh.models.repo_find.Posts;
import com.karka_deh.models.requests.PostRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Pageable;
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
        // it's unique becuase it's going to be in the url
        new Column("slug", "VARCHAR(255)", "NOT NULL", "UNIQUE"),
        new Column("content", "TEXT", "NOT NULL"),
        new Column("created_at", "TIMESTAMP WITH TIME ZONE", "DEFAULT", "CURRENT_TIMESTAMP"),
        new StandaloneConstraint("CONSTRAINT fk_posts_author FOREIGN KEY (author_id) REFERENCES users(id)"));

  }

  public void updateUserPost(UUID postId, Map<String, String> fields) {
    if (fields.isEmpty()) {
      return; // nothing to update
    }

    StringBuilder sql = new StringBuilder("UPDATE posts SET ");
    List<Object> values = new ArrayList<>();

    int i = 0;
    for (var entry : fields.entrySet()) {
      sql.append(entry.getKey()).append(" = ?");
      values.add(entry.getValue());

      if (i < fields.size() - 1) {
        sql.append(", ");
      }
      i++;
    }

    sql.append(" WHERE id = ?");
    values.add(postId);

    this.jdbc.update(sql.toString(), values.toArray());
  }

  public void deleteUserPost(String slug) {
    this.jdbc.update("DELETE FROM posts WHERE slug = ?", slug);
  }

  public Optional<UUID> findPostIdBySlug(String slug) {
    return this.findBySlug(slug).map(post -> post.getId());
  }

  public Optional<PostEntity> findBySlug(String slug) {
    String sql = "SELECT * FROM posts WHERE slug = ?";
    return this.jdbc.query(sql, new BeanPropertyRowMapper<>(PostEntity.class), slug).stream().findFirst();
  }

  public Posts findAllPostsByUserId(UUID id, Pageable pageable) {
    int size = pageable.getPageSize();
    int page = pageable.getPageNumber();

    int offset = size * page;

    String sql = """
        SELECT *, COUNT(*) OVER() as total_count
        FROM posts WHERE author_id = ?
        ORDER BY created_at DESC
        LIMIT ? OFFSET ?
        """;
    return this.jdbc.query(sql, rs -> {
      return this.collectPosts(rs, pageable);
    }, id, size, offset);
  }

  public Posts searchPosts(String keyword, Pageable pageable) {
    int size = pageable.getPageSize();
    int page = pageable.getPageNumber();

    int offset = size * page;

    String sql = """
          SELECT *, COUNT(*) OVER() AS total_count
          FROM posts
          WHERE LOWER(title) LIKE ? OR LOWER(content) LIKE ?
          ORDER BY created_at DESC
          LIMIT ? OFFSET ?
        """;

    String likeQuery = "%" + keyword.toLowerCase() + "%";

    return this.jdbc.query(sql, rs -> {
      return this.collectPosts(rs, pageable);

    }, likeQuery, likeQuery, size, offset);

  }

  private Posts collectPosts(ResultSet rs, Pageable pageable) throws SQLException {

    var posts = new ArrayList<PostEntity>();

    int totalCount = 0;
    var mapper = new BeanPropertyRowMapper<>(PostEntity.class);

    while (rs.next()) {
      // get every row except the last one, the `total_count`
      PostEntity post = mapper.mapRow(rs, rs.getRow() - 1);
      posts.add(post);

      if (totalCount == 0) {
        totalCount = rs.getInt("total_count");
      }
    }

    return new Posts(totalCount, posts, pageable);
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
    ps.setString(4, entity.getSlug());
    ps.setString(5, entity.getContent());
    ps.setTimestamp(6, timestamp);
    return ps;
  }

}
