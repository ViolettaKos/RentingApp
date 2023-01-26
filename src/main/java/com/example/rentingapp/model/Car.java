package com.example.project.model;


import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {

    private String brand;

    private String quality_class;

    private int price;

    private String name;

    private int car_id;

    private boolean isAvailable;


    public Car(int car_id, String brand, String quality_class, String name, int price,
               boolean isAvailable) {
        this.car_id=car_id;
        this.brand = brand;
        this.quality_class = quality_class;
        this.price = price;
        this.name = name;
        this.isAvailable=isAvailable;
    }

    public Car() {

    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQuality_class() {
        return quality_class;
    }

    public void setQuality_class(String quality_class) {
        this.quality_class = quality_class;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCar_id() {
        return car_id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", quality_class='" + quality_class + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", car_id=" + car_id +
                ", isAvailable=" + isAvailable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return price == car.price && car_id == car.car_id && isAvailable == car.isAvailable && brand.equals(car.brand) && quality_class.equals(car.quality_class) && name.equals(car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, quality_class, price, name, car_id, isAvailable);
    }
}
