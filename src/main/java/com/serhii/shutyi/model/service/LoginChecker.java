package com.serhii.shutyi.model.service;


import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.model.entity.User;

import java.util.Optional;

public class LoginChecker {

    public static boolean checkLogin(String enterLogin, String enterPass) {
        boolean result = false;

        try(UserDAO userDAO = DaoFactory.getInstance().createUserDAO()) {
            Optional<User> user = userDAO.findByEmail(enterLogin);

            result = user.get().getPassword().equals(enterPass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

