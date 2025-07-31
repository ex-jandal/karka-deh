
package com.karka_deh.models.repo_find;

import java.util.List;

import com.karka_deh.models.entities.CommentEntity;

public class Comments {

  private int count;

  private List<CommentEntity> commentEntities;

  public Comments(int count, List<CommentEntity> commentEntities) {
    this.count = count;
    this.commentEntities = commentEntities;
  }

  public List<CommentEntity> getCommentEntities() {
    return commentEntities;
  }

  public int getCount() {
    return count;
  }

}
