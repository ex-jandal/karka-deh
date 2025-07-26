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

import com.karka_deh.models.db.Column;
import com.karka_deh.models.db.StandaloneConstraint;
import com.karka_deh.models.db.TableElement;
import com.karka_deh.models.entities.CommentEntity;

@Repository
@DependsOn("postRepo")
public class CommentRepo extends BaseRepo<CommentEntity> {

  public CommentRepo(JdbcTemplate jdbc) {
    super(jdbc, CommentEntity.class, "comments");
  }

  @Override
  protected List<TableElement> getTableStructure() {
    return List.of(
        new Column("id", "VARCHAR2(36)", "NOT", "NULL", "PRIMARY", "KEY"),
        new Column("post_id", "VARCHAR2(36)", "NOT", "NULL"),
        new Column("author_id", "VARCHAR2(36)", "NOT", "NULL"),
        new Column("content", "CLOB", "NOT", "NULL"),
        new Column("created_at", "TIMESTAMP", "DEFAULT", "CURRENT_TIMESTAMP"),
        new StandaloneConstraint("CONSTRAINT fk_comment_post_post FOREIGN KEY (post_id) REFERENCES posts(id)"),
        new StandaloneConstraint("CONSTRAINT fk_comment_author_users FOREIGN KEY (author_id) REFERENCES users(id)"));

  }

  public List<CommentEntity> getAllPostCommentsByUser(UUID postId, String username) {
    // select everything from comments and alias it to 'c'
    // join users and alias it to 'u', and join them based on the author_id and id
    // of the users
    // now we get every comment where the post_id matches ouer, and with the same
    // username
    // because a user can comment multiple times
    String sql = """
        SELECT c.* FROM comments c
        JOIN users u ON c.author_id = u.id
        WHERE c.post_id = ? AND u.username = ?
        """;

    return this.jdbc.query(sql, new BeanPropertyRowMapper<>(CommentEntity.class), postId.toString(), username);
  }

  @Override
  protected PreparedStatement getInsertPreparedStatementSetter(Connection conn, String sql, CommentEntity entity)
      throws SQLException {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, entity.getId().toString());
    ps.setString(2, entity.getPostId());
    ps.setString(3, entity.getAuthorId());
    ps.setCharacterStream(4, new StringReader(entity.getContent()), entity.getContent().length());
    ps.setTimestamp(5, timestamp);

    return ps;

  }

}
