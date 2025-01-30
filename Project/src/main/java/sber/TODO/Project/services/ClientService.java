package sber.TODO.Project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.repositories.ClientRepository;

import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }

    public boolean existByLogin(Client client) {
        return clientRepository.existsByLogin(client.getLogin());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> optionalClient = clientRepository.findByLogin(username);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            return User.builder()
                    .username(client.getLogin())
                    .password(client.getPassword())
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public boolean existByLoginAndPasswordAndEmail(Client client) {
        return clientRepository.existsByLoginAndPasswordAndEmail(client.getLogin(), client.getPassword(), client.getEmail());
    }

    public Client findByLogin(String username) {
        return clientRepository.findByLogin(username).get();
    }
}
