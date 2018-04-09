package com.xg.api.model.stock;

import com.xg.api.model.BaseModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class StockHisDividend extends BaseModel {
    //{"date":"2017-07-07","code":"600519","year":"2016年报","executeDate":"2017-07-01","remark":"10派67.87元(含税)","percent":1.44}
    @Id
    @GeneratedValue
    private Integer id;
    private String code;
    private String title;
    private String executeDate;
    private String remark;
    private Double percent;
}
