package com.xg.service;

import com.xg.api.model.stock.StockHisRoe;
import com.xg.repository.stock.StockHisRoeRepository;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author chenguoxiang
 * @create 2018-03-09 14:57
 **/
@Service
public class StockHisRoeService {

    @Resource
    private StockSpider spider;
    @Resource
    private StockHisRoeRepository repository;

    /**
     * 增加一个代码的历史roe
     * @param code
     * @throws Exception
     */
    public List<StockHisRoe> addStockHisRoe(String code) throws Exception {
        JSONArray jsons=spider.getHistoryROE(code);
        List<StockHisRoe> lis = JSON.parseArray(jsons.toJSONString(),StockHisRoe.class);
        template.remove(new Query(Criteria.where("code").is(code)),StockHisRoe.class);
        repository.save(lis);
        return lis;

    }
}
