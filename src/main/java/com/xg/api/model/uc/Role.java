package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
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
public class Role implements Serializable {
  @Id
  @GeneratedValue
  private Integer id;
  @Column(length = 32)
  private String name;
  @Column(length = 32,unique = true)
  private String code;
  @Column
  private String description;

  @OneToMany(mappedBy = "role")
  private List<Permission> permissions;

  @OneToMany(mappedBy = "role")
  @JsonBackReference
  private List<UserRoleRef> userRoleRefs;

  public List<UserRoleRef> getUserRoleRefs() {
    return userRoleRefs;
  }

  public void setUserRoleRefs(List<UserRoleRef> userRoleRefs) {
    this.userRoleRefs = userRoleRefs;
  }

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

}
