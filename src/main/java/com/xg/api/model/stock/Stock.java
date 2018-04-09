package com.xg.api.model.stock;

import com.xg.api.model.BaseModel;
import java.util.Date;
import javax.persistence.Entity;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class Stock extends BaseModel{
    @Id
    private String code;
    private String type;
    private String name;
    private Double price;
    private Double yesterdayPrice;
    private Double fluctuate;
    private Double todayMax;
    private Double todayMin;
    private Date priceDate;
    private String industry;
    private String mainBusiness;
    private Double totalValue;
    private Double pb;
    private Double roe;
    private Double bvps;
    private Double pes;
    private Double ped;
    private Integer infodate;
    private String dividendDate;
    private Double dividend;
    private Integer dividendUpdateDay;
    private Double dy;//实时股息率
    private Integer dyDate;//实时股息更新时间
}
