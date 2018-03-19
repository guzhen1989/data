package com.xg.api.model.uc;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * 权限
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
public class Permission {
  @Id @GeneratedValue private Integer id;

  @Column(length = 32)
  private String name;

  @Column private String description;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Role role;

  @ManyToMany
  @JoinTable(
    name = "permission_resource_ref",
    joinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id")}
  )
  private List<Resource> resources;

  public List<Resource> getResources() {
    return resources;
  }

  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
