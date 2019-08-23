package com.zzz.service;

import com.zzz.entity.Goods;

import java.util.List;

public interface GoodsService {

    void addGoods(Goods goods);

    Goods findById(Long id);

    List<Goods> listByKeyword(String keyword);
}
