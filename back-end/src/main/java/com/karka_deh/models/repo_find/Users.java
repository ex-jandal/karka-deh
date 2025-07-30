
package com.karka_deh.models.repo_find;

import java.util.List;

import com.karka_deh.models.entities.PostEntity;
import com.karka_deh.models.entities.UserEntity;

public class Users {

  private int count;

  private List<UserEntity> userEntities;

  public Users(int count, List<UserEntity> userEntities) {
    this.count = count;
    this.userEntities = userEntities;
  }

  public int getCount() {
    return count;
  }

  public List<UserEntity> getUserEntities() {
    return userEntities;
  }

}
