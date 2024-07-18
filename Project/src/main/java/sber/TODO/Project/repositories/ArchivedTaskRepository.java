package sber.TODO.Project.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sber.TODO.Project.entities.ArchivedTask;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.entities.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface ArchivedTaskRepository extends JpaRepository<ArchivedTask, Long> {
    @Query("SELECT t FROM ArchivedTask t WHERE (t.name LIKE %?1% or t.description LIKE %?1%) AND client=?2")
    List<ArchivedTask> findAllByStringAndClient(String search, Client client, Sort ascending);

    List<ArchivedTask> findByDateBefore(LocalDateTime now);

    List<ArchivedTask> findByClient(Client client);
}
