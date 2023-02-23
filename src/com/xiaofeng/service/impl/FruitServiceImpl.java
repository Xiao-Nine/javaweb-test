package com.xiaofeng.service.impl;

import com.xiaofeng.dao.Fruits;
import com.xiaofeng.pojo.Fruit;
import com.xiaofeng.service.FruitService;

import java.util.List;

public class FruitServiceImpl implements FruitService {
    private Fruits fruits = Fruits.getInstance();
    @Override
    public boolean addFruit(Fruit fruit) {
        return fruits.addFruit(fruit);
    }

    @Override
    public boolean removeFruitByName(String name) {
        return fruits.removeFruitByName(name);
    }

    @Override
    public List<Fruit> getFruitList() {
        return fruits.getFruitList();
    }

    @Override
    public Fruit getFruitByName(String name) {
        return fruits.getFruitByName(name);
    }

    @Override
    public boolean updateFruitList(String name, double price, int count) {
        return fruits.updateFruitList(name, price, count);
    }
}
