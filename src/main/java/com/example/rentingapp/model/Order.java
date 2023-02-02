package com.example.rentingapp.model;


import java.util.Objects;

public class Order extends Entity {
    private String login;
    private boolean isPayed;
    private boolean isRejected;
    private boolean isReturned;
    private int total_price;
    private int car_id;
    private String from;
    private String to;
    private long total_days;
    private boolean option;
    private String comment;

    public Order(String login, boolean isPayed, boolean isRejected, int total_price, int car_id,
                 String from, String to, long total_days, boolean option, String comment, boolean isReturned) {
        this.login = login;
        this.isPayed = isPayed;
        this.total_price = total_price;
        this.car_id = car_id;
        this.from = from;
        this.to = to;
        this.total_days = total_days;
        this.option = option;
        this.isRejected=isRejected;
        this.comment=comment;
        this.isReturned=isReturned;
    }

    public Order() {
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getTotal_days() {
        return total_days;
    }

    public void setTotal_days(long total_days) {
        this.total_days = total_days;
    }

    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }


    @Override
    public String toString() {
        return "Order{" +
                ", login='" + login + '\'' +
                ", isPayed=" + isPayed +
                ", isRejected=" + isRejected +
                ", total_price=" + total_price +
                ", car_id=" + car_id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", total_days=" + total_days +
                ", option=" + option +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return isReturned == order.isReturned && isPayed == order.isPayed && isRejected == order.isRejected && total_price == order.total_price && car_id == order.car_id && total_days == order.total_days && option == order.option && login.equals(order.login) && from.equals(order.from) && to.equals(order.to) && comment.equals(order.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, isPayed, isRejected, isReturned, total_price, car_id, from, to, total_days, option, comment);
    }
}
