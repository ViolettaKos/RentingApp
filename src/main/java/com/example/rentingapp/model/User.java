package com.example.rentingapp.model;

import java.util.Objects;


public class User extends Entity {

    private String role;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String telephone;

    private String password;

    private boolean blocked;

    private int money;

    public User(String fn, String ln, String username, String email, String pass, String number, Role role,
                boolean blocked, int money) {
        this.firstName = fn;
        this.lastName = ln;
        this.username = username;
        this.email = email;
        this.password = pass;
        this.telephone = number;
        this.role = role.getName();
        this.money = money;
        this.blocked = blocked;
    }

    public User() {
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "role='" + role + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                ", money=" + money +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return blocked == user.blocked && money == user.money && role.equals(user.role) && username.equals(user.username) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && email.equals(user.email) && telephone.equals(user.telephone) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, username, firstName, lastName, email, telephone, password, blocked, money);
    }
}
