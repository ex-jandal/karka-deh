package com.karka_deh.models.repo_find;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.karka_deh.models.entities.PostEntity;

public class Posts {

  private int count;

  private List<PostEntity> postEntities;

  private Pageable pageable;

  public Posts(int count, List<PostEntity> postEntities, Pageable pageable) {
    this.count = count;
    this.postEntities = postEntities;
    this.pageable = pageable;
  }

  public int getCount() {
    return count;
  }

  public List<PostEntity> getPostEntities() {
    return postEntities;
  }

  public <T> Page<T> toPage(
      Function<PostEntity, T> mapper) {

    int total = this.getCount();

    List<T> responses = this.postEntities.stream()
        .map(mapper)
        .toList();

    return new PageImpl<>(responses, this.pageable, total);
  }

}
