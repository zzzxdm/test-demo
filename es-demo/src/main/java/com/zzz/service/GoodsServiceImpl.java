package com.zzz.service;

import com.google.common.collect.Lists;
import com.zzz.dao.GoodsDao;
import com.zzz.entity.Goods;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public void addGoods(Goods goods) {
        goodsDao.save(goods);
    }

    @Override
    public Goods findById(Long id) {
        return goodsDao.findById(id).orElse(null);
    }

    @Override
    public List<Goods> listByKeyword(String keyword) {
        TermQueryBuilder termQuery = QueryBuilders.termQuery("desc", keyword);
        Iterable<Goods> goodsIterable = goodsDao.search(termQuery);
        return Lists.newArrayList(goodsIterable);
    }
}
