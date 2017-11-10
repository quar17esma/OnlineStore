package com.serhii.shutyi.service.impl;

import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.exceptions.BusyEmailException;
import com.serhii.shutyi.service.IClientsService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ClientsService extends Service implements IClientsService {
    private static final Logger LOGGER = Logger.getLogger(ClientsService.class);

    private ClientsService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static ClientsService INSTANCE =
                new ClientsService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static ClientsService getInstance() {
        return ClientsService.Holder.INSTANCE;
    }

    public void registerClient(Client client) throws BusyEmailException {

        try (Connection connection = connectionPool.getConnection();
             UserDAO userDAO = factory.createUserDAO(connection);
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
            connection.setAutoCommit(true);
        } catch (BusyEmailException e) {
            throw new BusyEmailException(e);
        } catch (Exception e) {
            LOGGER.error("Fail to register client", e);
            throw new RuntimeException(e);
        }
    }

    public Client getClientByEmail(String email) {
        Optional<Client> client;

        try (Connection connection = connectionPool.getConnection();
             UserDAO userDAO = factory.createUserDAO(connection);
             ClientDAO clientDAO = factory.createClientDAO(connection)) {
            connection.setAutoCommit(false);

            Optional<User> user = userDAO.findByEmail(email);
            client = clientDAO.findById(user.get().getId());

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            LOGGER.error("Fail to get client by email", e);
            throw new RuntimeException(e);
        }

        return client.get();
    }

    public List<Client> getClientsWithUnpaidOrders() {
        try (Connection connection = connectionPool.getConnection();
             ClientDAO clientDAO = factory.createClientDAO(connection)) {
            return clientDAO.findWithUnpaidOrders();
        } catch (Exception e) {
            LOGGER.error("Fail to get clients with unpaid orders", e);
            throw new RuntimeException(e);
        }
    }

    public void blockClientById(int clientId) {

        try (Connection connection = connectionPool.getConnection();
             ClientDAO clientDAO = factory.createClientDAO(connection)) {
            connection.setAutoCommit(false);

            Client client = clientDAO.findById(clientId).get();
            client.setIsInBlackList(true);
            clientDAO.update(client);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            LOGGER.error("Fail to block client", e);
            throw new RuntimeException(e);
        }
    }
}
