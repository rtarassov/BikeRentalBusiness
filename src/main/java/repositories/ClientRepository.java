package repositories;

import model.Client;
import util.CustomException;
import util.ExceptionMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRepository {
    List<Client> clients = new ArrayList<>();

    public void addClient(Client client) throws CustomException {
        if (clients.stream().anyMatch(clientId -> clientId.getId() == client.getId())) {
            throw new CustomException(ExceptionMessage.CLIENT_ALREADY_EXISTS);
        }
        clients.add(client);
    }

    public void removeClient(int id) throws CustomException {
        if (clients.stream().anyMatch(clientId -> clientId.getId() == clientId.getId())) {
            throw new CustomException(ExceptionMessage.NO_SUCH_CLIENT_WITH_ID);
        }
        clients = clients.stream()
                .filter(client -> client.getId() != id)
                .collect(Collectors.toList());
    }

    public void updateClient(int id, Client client) throws CustomException {
        removeClient(id);
        addClient(client);
    }

    public Client findClientById(int id) throws CustomException {
        return clients
                .stream()
                .filter(client -> client.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CustomException(ExceptionMessage.NO_SUCH_CLIENT_WITH_ID));
    }

    public void listClients() {
        for (Client client : clients) {
            System.out.println(client);
        }
    }
}
