
package com.karka_deh.models.repo_find;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.karka_deh.models.entities.CommentEntity;

public class Comments {

  private int count;
  private List<CommentEntity> commentEntities;

  private Pageable pageable;

  public Comments(int count, List<CommentEntity> commentEntities, Pageable pageable) {
    this.count = count;
    this.commentEntities = commentEntities;
    this.pageable = pageable;
  }

  public List<CommentEntity> getCommentEntities() {
    return commentEntities;
  }

  public int getCount() {
    return count;
  }

  public <T> Page<T> toPage(
      Function<CommentEntity, T> mapper) {

    int total = this.getCount();

    List<T> responses = this.commentEntities.stream()
        .map(mapper)
        .toList();

    return new PageImpl<>(responses, this.pageable, total);
  }

}
