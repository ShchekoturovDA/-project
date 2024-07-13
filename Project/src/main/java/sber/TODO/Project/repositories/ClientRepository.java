package sber.TODO.Project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.TODO.Project.entities.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    public boolean existsByLogin(String login);

    public Client findByLogin(String login);

    public boolean existsByLoginAndPasswordAndEmail(String login, String password, String email);

}
