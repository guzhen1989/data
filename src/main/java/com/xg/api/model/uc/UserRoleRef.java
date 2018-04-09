package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
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
 * 用户权限关联
 *
 * @author guzhen
 * @date 2018/3/28
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})})
@Data
public class UserRoleRef implements Serializable {
  @Id
  @GeneratedValue
  private Integer id;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "user_id",referencedColumnName = "id")
  private User user;
  @ManyToOne
  @JoinColumn(name = "role_id",referencedColumnName = "id")
  private Role role;

}
