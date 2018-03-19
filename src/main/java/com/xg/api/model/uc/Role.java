package com.xg.api.model.uc;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * 角色
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
public class Role {
  @Id
  @GeneratedValue
  private Integer id;
  @Column(length = 32)
  private String name;
  @Column(length = 32,unique = true)
  private String code;
  @Column
  private String description;
  @ManyToMany(mappedBy = "roles")
  private List<User> users;
  @OneToMany(mappedBy = "role")
  private List<Permission> permissions;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
