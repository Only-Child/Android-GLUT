package com.example.wechat.pojo;

public class Goods {
  private String name;
  private String category;
  private Double price;
  private String src;
  private int storage;

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", src='" + src + '\'' +
                ", storage=" + storage +
                '}';
    }

    public Goods() {

    }
    public Goods(String name, String category, Double price, String src, int storage) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.src = src;
        this.storage = storage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public String getSrc() {
        return src;
    }

    public int getStorage() {
        return storage;
    }
}
