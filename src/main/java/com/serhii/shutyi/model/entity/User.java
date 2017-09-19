package com.serhii.shutyi.model.entity;

import com.serhii.shutyi.model.enums.Role;

public class User {
    private int id;
    private String email;
    private String password;
    private boolean enabled;
    private Client client;
    private Role role;

    public User() {
    }

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
