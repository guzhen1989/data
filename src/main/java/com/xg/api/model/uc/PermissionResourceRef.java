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
 * 权限资源关系
 *
 * @author guzhen
 * @date 2018/3/30
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"permission_id","resource_id"})})
@Data
public class PermissionResourceRef implements Serializable{
  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  @JsonBackReference
  private Permission permission;

  @ManyToOne
  private Resource resource;
}
