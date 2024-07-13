package sber.TODO.Project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.repositories.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }

    public boolean existByLogin(Client client) {
        return clientRepository.existsByLogin(client.getLogin());
    }

    public boolean exist(Client client){
        return clientRepository.existsByLoginAndPasswordAndEmail(client.getLogin(), client.getPassword(), client.getEmail());
    }

    public Client findByLogin(String login){
        return clientRepository.findByLogin(login);
    }
}
