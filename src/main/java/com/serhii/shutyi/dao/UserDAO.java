package com.serhii.shutyi.dao;

import com.serhii.shutyi.entity.User;

import java.util.Optional;

public interface UserDAO extends GenericDAO<User> {
    Optional<User> findByEmail(String email);
}
