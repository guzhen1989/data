package com.xg.api.model.uc;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.springframework.http.HttpMethod;

/**
 * 资源
 *
 * @author guzhen
 * @date 2018/3/19
 */
@Entity
public class Resource {
  @Id @GeneratedValue private Integer id;

  @Column(length = 32)
  private String name;

  @Column private String url;

  @Column(length = 32)
  @Enumerated(EnumType.STRING)
  private HttpMethod method;

  @ManyToMany(mappedBy = "resources")
  private List<Permission> permissions;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Resource resource = (Resource) o;

    if (url != null ? !url.equals(resource.url) : resource.url != null) {
      return false;
    }
    return method == resource.method;
  }

  @Override
  public int hashCode() {
    int result = url != null ? url.hashCode() : 0;
    result = 31 * result + (method != null ? method.hashCode() : 0);
    return result;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
  }
}
