package com.xg.api.model.stock;

import com.xg.api.model.BaseModel;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class StockHisRoe extends BaseModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String code;
    private Integer year;
    private Double roe;
    private Double roetb;
    private Date date;
}
