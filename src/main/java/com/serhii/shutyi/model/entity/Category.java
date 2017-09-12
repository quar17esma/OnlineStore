package com.serhii.shutyi.model.entity;

import java.util.List;

public class Category {
    int id;
    String title;
    String description;
    List<Good> goods;

    public Category(int id, String title, String description, List<Good> goods) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.goods = goods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", goods=" + goods +
                '}';
    }
}
