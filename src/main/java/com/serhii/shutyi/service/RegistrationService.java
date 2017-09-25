package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.exceptions.BusyEmailException;

import java.util.Optional;

public class RegistrationService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static RegistrationService INSTANCE = new RegistrationService();
    }

    public static RegistrationService getInstance() {
        return RegistrationService.Holder.INSTANCE;
    }

    public void registerClient(Client client) throws BusyEmailException {

        try (UserDAO userDAO = factory.createUserDAO();
             ClientDAO clientDAO = factory.createClientDAO()) {


            Optional<User> user = userDAO.findByEmail(client.getUser().getEmail());
            if (user.isPresent()) {
                throw new BusyEmailException("Fail to register client, email is busy",
                        client.getName(), client.getUser().getEmail());
            }

            int userId = userDAO.insert(client.getUser());
            client.setId(userId);
            clientDAO.insert(client);
        } catch (BusyEmailException e) {
            throw new BusyEmailException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
