package com.xg.api.model.uc;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xg.api.model.BaseModel;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpMethod;

/**
 * 资源
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
@EqualsAndHashCode(of = {"url","method"}, callSuper = false)
@Data
public class Resource extends BaseModel {
  @Id @GeneratedValue private Integer id;

  @Column(length = 32)
  private String name;

  @Column private String url;

  @Column(length = 32)
  @Enumerated(EnumType.STRING)
  private HttpMethod method;

  @OneToMany(mappedBy = "resource")
  @JsonBackReference
  private List<PermissionResourceRef> permissionResourceRefs;
}
