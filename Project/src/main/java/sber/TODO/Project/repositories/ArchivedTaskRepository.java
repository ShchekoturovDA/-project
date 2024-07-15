package sber.TODO.Project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sber.TODO.Project.entities.ArchivedTask;
import sber.TODO.Project.entities.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface ArchivedTaskRepository extends JpaRepository<ArchivedTask, Long> {
    @Query("SELECT t FROM ArchivedTask t WHERE t.name LIKE %?1% or t.description LIKE %?1%")
    List<ArchivedTask> findAllByString(String search);

    @Query("SELECT t FROM ArchivedTask t WHERE (t.name LIKE %?1% or t.description LIKE %?1%) and t.date <= ?2")
    List<ArchivedTask> findAllByStringAndDate(String search, LocalDateTime date);

}
