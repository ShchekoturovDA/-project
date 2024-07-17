package sber.TODO.Project.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.entities.Task;
import sber.TODO.Project.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll(Sort.by("date", "prior").ascending());
    }

    public List<Task> findByDone(boolean done) {
        return taskRepository.findAllByDone(done, Sort.by("date", "prior").ascending());
    }

    public Optional<Task> findOneById(long id) {
        return taskRepository.findById(id);
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }


    public List<Task> findByClient(Client client) {
        return taskRepository.findAllByClient(client, Sort.by("date", "prior").ascending());
    }

    public List<Task> findAllByStringAndUser(String search, Client client) {
        return taskRepository.findAllByStringAndClient(search, client, Sort.by("date", "prior").ascending());
    }

    public List<Task> findByDateBeforeAndDone(LocalDateTime now, boolean done) {
        return taskRepository.findByDateBeforeAndDone(now, done);
    }

    public void prolong(List<Task> tasks) {
        for (Task task : tasks) {
            switch (task.getRepeatable()) {
                case ONCE -> task.setDone(true);
                case DAY -> task.setDate(task.getDate().plusDays(1));
                case WEEK -> task.setDate(task.getDate().plusWeeks(1));
                case MONTH -> task.setDate(task.getDate().plusMonths(1));
            }
            save(task);
        }
    }
}
