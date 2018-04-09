package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色权限关系
 *
 * @author guzhen
 * @date 2018/3/30
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id","permission_id"})})
@Data
public class RolePermissionRef {

  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "role_id",referencedColumnName = "id")
  @JsonBackReference
  private Role role;

  @ManyToOne
  @JoinColumn(name = "permission_id",referencedColumnName = "id")
  private Permission permission;
}
