package com.xg.api.model.stock;

import com.xg.api.model.BaseModel;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 码
 *
 * @author guzhen
 * @date 2018/7/10
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class StockCode extends BaseModel{
  @Id
  private String code;

  private String name;


}
