package com.serhii.shutyi.service;

import com.serhii.shutyi.dao.ClientDAO;
import com.serhii.shutyi.dao.ConnectionPool;
import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.UserDAO;
import com.serhii.shutyi.entity.Client;
import com.serhii.shutyi.entity.User;
import com.serhii.shutyi.exceptions.BusyEmailException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ClientsService {
    private DaoFactory factory;
    private ConnectionPool connectionPool;

    public ClientsService(DaoFactory factory, ConnectionPool connectionPool) {
        this.factory = factory;
        this.connectionPool = connectionPool;
    }

    private static class Holder {
        private static ClientsService INSTANCE =
                new ClientsService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static ClientsService getInstance() {
        return ClientsService.Holder.INSTANCE;
    }

    public void registerClient(Client client) throws BusyEmailException {

        Connection connection = connectionPool.getConnection();
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

    public Client getClientByEmail(String email) {
        Client client = null;

        Connection connection = connectionPool.getConnection();
        try (UserDAO userDAO = factory.createUserDAO(connection);
             ClientDAO clientDAO = factory.createClientDAO(connection)) {
            connection.setAutoCommit(false);

            User user = userDAO.findByEmail(email).get();
            client = clientDAO.findById(user.getId()).get();

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    public List<Client> getClientsWithUnpaidOrders() {
        List<Client> clients = null;

        Connection connection = connectionPool.getConnection();
        try (ClientDAO clientDAO = factory.createClientDAO(connection)) {
            clients = clientDAO.findWithUnpaidOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }

    public void blockClientById(int clientId) {

        Connection connection = connectionPool.getConnection();
        try (ClientDAO clientDAO = factory.createClientDAO(connection)) {
            connection.setAutoCommit(false);

            Client client = clientDAO.findById(clientId).get();
            client.setIsInBlackList(true);
            clientDAO.update(client);

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
