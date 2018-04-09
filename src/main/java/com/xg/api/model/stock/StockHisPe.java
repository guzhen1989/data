package com.xg.api.model.stock;

import com.xg.api.model.BaseModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class StockHisPe extends BaseModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String code;
    private String date;
    private Double pe;
    private Double price;
}
