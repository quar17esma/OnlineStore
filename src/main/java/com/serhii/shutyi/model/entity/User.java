package com.serhii.shutyi.model.entity;

import com.serhii.shutyi.model.enums.Role;

public class User {
    private int id;
    private String email;
    private String password;
    private boolean enabled;
    private Role role;

    public User() {
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
                ", role=" + role +
                '}';
    }

    public static class Builder {
        private  User user;

        public Builder() {
            this.user = new User();
        }

        public User build() {
            return user;
        }

        public Builder setId(final int id) {
            user.setId(id);
            return this;
        }

        public Builder setEmail(final String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setPassword(final String password) {
            user.setPassword(password);
            return this;
        }

        public Builder setEnabled(final boolean enabled) {
            user.setEnabled(enabled);
            return this;
        }

        public Builder setRole(final Role role) {
            user.setRole(role);
            return this;
        }
    }
}
