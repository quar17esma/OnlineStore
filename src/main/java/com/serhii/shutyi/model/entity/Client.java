package com.serhii.shutyi.model.entity;

public class Client {
    private int id;
    private String firstName;
    private int discount;
    private User user;


    public Client(int id, String firstName, int discount, User user) {
        this.id = id;
        this.firstName = firstName;
        this.discount = discount;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getDiscount() {
        return discount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", discount=" + discount +
                ", user=" + user +
                '}';
    }
}
