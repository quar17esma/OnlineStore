package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.Client;

public class RegistrationService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static RegistrationService INSTANCE = new RegistrationService();
    }

    public static RegistrationService getInstance() {
        return RegistrationService.Holder.INSTANCE;
    }

    public void registerClient(Client client) {

        try (UserDAO userDAO = factory.createUserDAO();
             ClientDAO clientDAO = factory.createClientDAO()) {

            int userId = userDAO.insert(client.getUser());
            client.setId(userId);
            clientDAO.insert(client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
