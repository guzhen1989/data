package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 权限
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
public class Permission extends BaseModel {
  @Id @GeneratedValue private Integer id;

  @Column(length = 32)
  private String name;

  @Column private String description;

  @OneToMany(mappedBy = "permission")
  @JsonBackReference
  private List<RolePermissionRef> rolePermissionRefs;

  @OneToMany(mappedBy = "permission")
  private List<PermissionResourceRef> permissionResourceRefs;

  @ManyToOne
  @JoinColumn(name = "permission_id", referencedColumnName = "id")
  private Permission permission;

  public List<RolePermissionRef> getRolePermissionRefs() {
    return rolePermissionRefs;
  }

  public void setRolePermissionRefs(
      List<RolePermissionRef> rolePermissionRefs) {
    this.rolePermissionRefs = rolePermissionRefs;
  }

  public List<PermissionResourceRef> getPermissionResourceRefs() {
    return permissionResourceRefs;
  }

  public void setPermissionResourceRefs(
      List<PermissionResourceRef> permissionResourceRefs) {
    this.permissionResourceRefs = permissionResourceRefs;
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

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }
}
