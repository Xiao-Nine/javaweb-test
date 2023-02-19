package com.xiaofeng.dao;

import com.xiaofeng.pojo.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * 单例模式存储FruitList
 */
public class Fruits {
    private static Fruits instance = new Fruits();
    private List<Fruit> fruitList;
    private Fruits() {
        fruitList = new ArrayList<>();
        fruitList.add(new Fruit("苹果", 5, 3));
        fruitList.add(new Fruit("香蕉", 5, 3));
        fruitList.add(new Fruit("桃子", 5, 3));
        fruitList.add(new Fruit("榴莲", 5, 3));
        fruitList.add(new Fruit("苹果", 5, 3));
        fruitList.add(new Fruit("梨子", 5, 3));
        fruitList.add(new Fruit("橘子", 5, 3));
    }
    public static Fruits getInstance() {
        return instance;
    }
    public boolean addFruit(Fruit newFruit) {
        for (int i = 0; i < fruitList.size(); i++) {
            if (fruitList.get(i).getName().equals(newFruit.getName())) return false;
        }
        fruitList.add(newFruit);
        return true;
    }
    public boolean removeFruitByName(String name) {
        for (int i = 0; i < fruitList.size(); i++) {
            if (fruitList.get(i).getName().equals(name)) {
                fruitList.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Fruit> getFruitList() {
        return fruitList;
    }

    public Fruit getFruitByName(String name) {
        for (Fruit fruit : fruitList) {
            if (fruit.getName().equals(name)) return fruit;
        }
        return null;
    }

    public boolean updateFruitList(String name, double price, int count) {
        for (Fruit fruit : fruitList) {
            if (fruit.getName().equals(name)) {
                fruit.setCount(count);
                fruit.setPrice(price);
                return true;
            }
        }
        return false;
    }
}