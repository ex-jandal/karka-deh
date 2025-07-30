package com.karka_deh.models.repo_find;

import java.util.List;

import com.karka_deh.models.entities.PostEntity;

public class Posts {

  private int count;

  private List<PostEntity> postEntities;

  public Posts(int count, List<PostEntity> postEntities) {
    this.count = count;
    this.postEntities = postEntities;
  }

  public int getCount() {
    return count;
  }

  public List<PostEntity> getPostEntities() {
    return postEntities;
  }

}
