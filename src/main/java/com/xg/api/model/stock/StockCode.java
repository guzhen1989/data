package com.xg.api.model.stock;

import com.xg.api.model.BaseModel;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
  public static final String EXCHANGE_SH="sh";
  public static final String EXCHANGE_SZ="sz";

  @Id
  private String code;

  private String name;

  /**
   * 交易所
   */
  private String exchange;

  /**
   * 数据日期
   */
  @Temporal(TemporalType.DATE)
  private Date dataDate;
}
