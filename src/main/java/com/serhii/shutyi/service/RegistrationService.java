package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.exceptions.BusyEmailException;

import java.sql.Connection;
import java.util.Optional;

public class RegistrationService {
    DaoFactory factory;
    Connection connection;

    public RegistrationService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static RegistrationService INSTANCE =
                new RegistrationService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static RegistrationService getInstance() {
        RegistrationService registrationService = RegistrationService.Holder.INSTANCE;
        registrationService.connection = ConnectionPool.getConnection();
        return RegistrationService.Holder.INSTANCE;
    }

    public void registerClient(Client client) throws BusyEmailException {

        try (UserDAO userDAO = factory.createUserDAO(connection);
             ClientDAO clientDAO = factory.createClientDAO(connection)) {

            connection.setAutoCommit(false);

            Optional<User> user = userDAO.findByEmail(client.getUser().getEmail());
            if (user.isPresent()) {
                throw new BusyEmailException("Fail to register client, email is busy",
                        client.getName(), client.getUser().getEmail());
            }

            int userId = userDAO.insert(client.getUser());
            client.setId(userId);
            clientDAO.insert(client);

            connection.commit();
        } catch (BusyEmailException e) {
            throw new BusyEmailException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
