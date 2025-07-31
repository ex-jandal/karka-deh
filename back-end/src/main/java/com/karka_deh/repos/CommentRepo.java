package com.karka_deh.repos;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.karka_deh.errors.SlugNotFoundException;
import com.karka_deh.models.db.Column;
import com.karka_deh.models.db.StandaloneConstraint;
import com.karka_deh.models.db.TableElement;
import com.karka_deh.models.entities.CommentEntity;

@Repository
@DependsOn("postRepo")
public class CommentRepo extends BaseRepo<CommentEntity> {

  private final PostRepo postRepo;

  public CommentRepo(JdbcTemplate jdbc, PostRepo postRepo) {
    super(jdbc, CommentEntity.class, "comments");

    this.postRepo = postRepo;
  }

  @Override
  protected List<TableElement> getTableStructure() {
    return List.of(
        new Column("id", "UUID", "PRIMARY KEY", "DEFAULT", "gen_random_uuid()"),
        new Column("post_id", "UUID", "NOT NULL"),
        new Column("author_id", "UUID", "NOT NULL"),
        new Column("content", "TEXT", "NOT NULL"),
        new Column("created_at", "TIMESTAMP WITH TIME ZONE", "DEFAULT", "CURRENT_TIMESTAMP"),
        new StandaloneConstraint("CONSTRAINT fk_comment_post_post FOREIGN KEY (post_id) REFERENCES posts(id)"),
        new StandaloneConstraint("CONSTRAINT fk_comment_author_users FOREIGN KEY (author_id) REFERENCES users(id)"));

  }

  public List<CommentEntity> getAllPostCommentsByUser(String slug, String username) {
    UUID postId = this.postRepo.findPostIdBySlug(slug).orElseThrow(() -> new SlugNotFoundException(slug));
    String sql = """
        -- select everything from comments and alias it to 'c'
        SELECT c.* FROM comments c

        -- join users and alias it to 'u', and join them based on the author_id and id of the users
        JOIN users u ON c.author_id = u.id

        -- now we get every comment where the post_id matches ouer, and with the same username
        -- because a user can comment multiple times
        WHERE c.post_id = ? AND u.username = ?
        """;

    return this.jdbc.query(sql, new BeanPropertyRowMapper<>(CommentEntity.class), postId, username);
  }

  @Override
  protected PreparedStatement getInsertPreparedStatementSetter(Connection conn, String sql, CommentEntity entity)
      throws SQLException {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setObject(1, entity.getId(), java.sql.Types.OTHER);
    ps.setObject(2, entity.getPostId(), java.sql.Types.OTHER);
    ps.setObject(3, entity.getAuthorId(), java.sql.Types.OTHER);
    ps.setString(4, entity.getContent());
    ps.setTimestamp(5, timestamp);

    return ps;

  }

}
