package com.xiaofeng.service;

import com.xiaofeng.pojo.Fruit;

import java.util.List;

public interface FruitService {
    public boolean addFruit(Fruit fruit);
    public boolean removeFruitByName(String name);
    public List<Fruit> getFruitList();
    public Fruit getFruitByName(String name);
    public boolean updateFruitList(String name, double price, int count);
}
