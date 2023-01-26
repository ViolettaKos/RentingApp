package com.example.rentingapp.model;

import java.util.Date;
import java.util.Objects;

public class OrderInfo {

    private int order_id;

    private int car_id;
    private String login;
    private Date from, to;
    private int total_days;
    private boolean option, isPayed, isRejected, isReturned;
    private String brand, name, quality_class;

    private int car_price;

    private int total_price;

    private String comment;

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public OrderInfo(int order_id, int car_id, String login, Date from, Date to, int total_days, boolean option,
                     boolean isPayed, boolean isRejected, boolean isReturned, String brand, String name, String quality_class, int car_price,
                     int total_price, String comment) {
        this.order_id = order_id;
        this.car_id = car_id;
        this.login = login;
        this.from = from;
        this.to = to;
        this.total_days = total_days;
        this.option = option;
        this.isPayed = isPayed;
        this.isReturned = isReturned;
        this.brand = brand;
        this.name = name;
        this.quality_class = quality_class;
        this.car_price = car_price;
        this.total_price=total_price;
        this.isRejected=isRejected;
        this.comment=comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public int getOrder_id() {
        return order_id;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getTotal_days() {
        return total_days;
    }

    public void setTotal_days(int total_days) {
        this.total_days = total_days;
    }

    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuality_class() {
        return quality_class;
    }

    public void setQuality_class(String quality_class) {
        this.quality_class = quality_class;
    }

    public int getCar_price() {
        return car_price;
    }

    public void setCar_price(int car_price) {
        this.car_price = car_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderInfo orderInfo = (OrderInfo) o;
        return order_id == orderInfo.order_id && car_id == orderInfo.car_id && total_days == orderInfo.total_days
                && option == orderInfo.option && isPayed == orderInfo.isPayed && isRejected == orderInfo.isRejected
                && isReturned == orderInfo.isReturned && car_price == orderInfo.car_price && total_price == orderInfo.total_price
                && login.equals(orderInfo.login) && from.equals(orderInfo.from) && to.equals(orderInfo.to) && brand.equals(orderInfo.brand)
                && name.equals(orderInfo.name) && quality_class.equals(orderInfo.quality_class) && comment.equals(orderInfo.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, car_id, login, from, to, total_days, option, isPayed, isRejected, isReturned, brand, name, quality_class, car_price, total_price, comment);
    }
}
