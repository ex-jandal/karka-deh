package com.karka_deh.models.repo_find;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.karka_deh.models.entities.PostEntity;

public class Entry<E> {

  private int count;

  private List<E> entities;

  private Pageable pageable;

  public Entry(int count, List<E> entities, Pageable pageable) {
    this.count = count;
    this.entities = entities;
    this.pageable = pageable;
  }

  public int getCount() {
    return count;
  }

  public List<E> getEntities() {
    return this.entities;
  }

  public <T> Page<T> toPage(
      Function<E, T> mapper) {

    int total = this.getCount();

    List<T> responses = this.entities.stream()
        .map(mapper)
        .toList();

    return new PageImpl<>(responses, this.pageable, total);
  }

}
