package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xg.api.model.BaseModel;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 * 权限
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
@Data
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
}
