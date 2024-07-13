package sber.TODO.Project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sber.TODO.Project.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
