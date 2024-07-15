package sber.TODO.Project.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sber.TODO.Project.entities.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByDone(boolean done, Sort ascending);

    @Query("SELECT t FROM Task t WHERE t.name LIKE %?1% or t.description LIKE %?1%")
    List<Task> findAllByString(String search, Sort ascending);

    @Query("SELECT t FROM Task t WHERE (t.name LIKE %?1% or t.description LIKE %?1%) and t.date <= ?2")
    List<Task> findAllByStringAndDate(String search, LocalDateTime date, Sort ascending);
}
