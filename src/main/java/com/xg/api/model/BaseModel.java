package com.xg.api.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 基础数据库实体
 *
 * @author guzhen
 * @date 2018/3/30
 */
@MappedSuperclass
@Data
public abstract class BaseModel implements Serializable{

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  @Column(updatable = false)
  private Date createTime;

  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date updateTime;
}
