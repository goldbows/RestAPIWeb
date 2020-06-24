package data.util;

public enum Price {
    AB(50),
    AC(100),
    AD(150),
    BC(50),
    BD(100),
    CD(50);

    Integer price;

    public Integer getPrice() {
        return this.price;
    }

    Price(Integer price) {
        this.price = price;
    }
}
