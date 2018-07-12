package com.xg.api.model.stock;

import com.xg.api.model.BaseModel;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日行情
 *
 * @author guzhen
 * @date 2018/7/12
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code","date"}))
@Data
@EqualsAndHashCode(callSuper = true)
public class StockDateInfo extends BaseModel{
  /** 股票代码 */
  @Id
  private String code;
  /** 名称 */
  private String name;
  /** 日期 */
  @Temporal(TemporalType.DATE)
  private Date date;
  /** 开盘价 */
  @Column(precision =10, scale = 2)
  private BigDecimal openPrice;
  /** 收盘价 */
  @Column(precision =10, scale = 2)
  private BigDecimal closePrice;
  /** 最低价 */
  @Column(precision =10, scale = 2)
  private BigDecimal lowPrice;
  /** 最高价 */
  @Column(precision =10, scale = 2)
  private BigDecimal highPrice;
  /** 前收盘 */
  @Column(precision =10, scale = 2)
  private BigDecimal yestClosePrice;

  /** 涨跌额 */
  @Column(precision =10, scale = 2)
  private BigDecimal changeMoney;
  /** 涨跌幅 */
  @Column(precision =10, scale = 5)
  private BigDecimal changeRate;
  /** 换手率 */
  @Column(precision =10, scale = 5)
  private BigDecimal turnoverRate;
  /** 成交量 */
  private Long volumeCount;
  /** 成交金额 */
  @Column(precision =20, scale = 2)
  private BigDecimal volumeMoney;
  /** 总市值 */
  @Column(precision =20, scale = 2)
  private BigDecimal totalValue;
  /** 流通市值 */
  @Column(precision =20, scale = 2)
  private BigDecimal circulationMarketValue;
}
