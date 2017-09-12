package com.serhii.shutyi.model.entity;

public class User {
    int id;
    String email;
    String password;
    boolean enabled;
    Client client;
    Role role;

    public User(int id, String email, String password, boolean enabled, Client client, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.client = client;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", client=" + client +
                ", role=" + role +
                '}';
    }
}
