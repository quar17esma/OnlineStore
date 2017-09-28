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
    DaoFactory factory;
    Connection connection;

    public ClientsService(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    private static class Holder {
        private static ClientsService INSTANCE =
                new ClientsService(DaoFactory.getInstance(), ConnectionPool.getConnection());
    }

    public static ClientsService getInstance() {
        ClientsService clientsService = ClientsService.Holder.INSTANCE;
        clientsService.connection = ConnectionPool.getConnection();
        return ClientsService.Holder.INSTANCE;
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

    public Client getClientByEmail(String email) {
        Client client = null;

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

        try (ClientDAO clientDAO = factory.createClientDAO(connection)) {
            clients = clientDAO.findWithUnpaidOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }

    public void blockClientById(int clientId) {

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
