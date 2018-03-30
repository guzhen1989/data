package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * 用户
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
public class User extends BaseModel{
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

  public List<UserRoleRef> getUserRoleRefs() {
    return userRoleRefs;
  }

  public void setUserRoleRefs(List<UserRoleRef> userRoleRefs) {
    this.userRoleRefs = userRoleRefs;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
