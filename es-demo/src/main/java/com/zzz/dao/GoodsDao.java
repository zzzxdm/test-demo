package com.zzz.dao;

import com.zzz.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsDao extends ElasticsearchRepository<Goods, Long> {
}
