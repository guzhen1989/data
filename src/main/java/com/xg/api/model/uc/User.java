package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xg.api.model.BaseModel;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 * 用户
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
@Data
public class User extends BaseModel {
  @Id
  @GeneratedValue
  private Integer id;

  @Column(length = 32, unique = true, nullable = false)
  private String username;

  @Column(length = 64)
  @JsonIgnore
  private String password;

  @OneToMany(mappedBy = "user")
  private List<UserRoleRef> userRoleRefs;

}
