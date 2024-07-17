package sber.TODO.Project.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.entities.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByDone(boolean done, Sort ascending);

    @Query("SELECT t FROM Task t WHERE (t.name LIKE %?1% or t.description LIKE %?1%) AND t.client=?2")
    List<Task> findAllByStringAndClient(String search, Client client, Sort ascending);

    List<Task> findAllByClient(Client client, Sort ascending);

    List<Task> findByDateBeforeAndDone(LocalDateTime now, boolean done);
}
