package com.xiaofeng.dao;

import com.xiaofeng.pojo.Fruit;

import java.util.ArrayList;
import java.util.List;

public class FruitDao {
    private static List<Fruit> fruits;

    public FruitDao() {
        if (fruits == null) this.fruits = new ArrayList<>();
    }

    public List<Fruit> getFruits() {
        if (fruits == null) {
            List<Fruit> fruits = new ArrayList<>();
            fruits.add(new Fruit("苹果", 5, 3));
            fruits.add(new Fruit("香蕉", 5, 3));
            fruits.add(new Fruit("桃子", 5, 3));
            fruits.add(new Fruit("榴莲", 5, 3));
            fruits.add(new Fruit("苹果", 5, 3));
            fruits.add(new Fruit("梨子", 5, 3));
            fruits.add(new Fruit("橘子", 5, 3));
        }
        return fruits;
    }

    public boolean addFruit(Fruit newFruit) {
        if (fruits == null) return false;
        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getName().equals(newFruit.getName())) return false;
        }
        fruits.add(newFruit);
        return true;
    }
    public boolean removeFruitByName(String name) {
        if (fruits == null) return false;
        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getName().equals(name)) {
                fruits.remove(i);
                return true;
            }
        }
        return false;
    }
}
