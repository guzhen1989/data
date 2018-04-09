package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xg.api.model.BaseModel;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 * 角色
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
@Data
public class Role extends BaseModel {
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
  private List<RolePermissionRef> rolePermissionRefs;

  @OneToMany(mappedBy = "role")
  @JsonBackReference
  private List<UserRoleRef> userRoleRefs;
}
