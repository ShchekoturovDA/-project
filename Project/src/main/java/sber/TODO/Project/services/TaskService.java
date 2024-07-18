package sber.TODO.Project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sber.TODO.Project.Repeatable;
import sber.TODO.Project.entities.ArchivedTask;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.entities.Task;
import sber.TODO.Project.repositories.ArchivedTaskRepository;
import sber.TODO.Project.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ArchivedTaskRepository archivedTaskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ArchivedTaskRepository archivedTaskRepository) {
        this.archivedTaskRepository = archivedTaskRepository;
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

    public void prolong(List<Task> tasks, List<ArchivedTask> archives) {
        for (Task task : tasks) {
            switch (task.getRepeatable()) {
                case ONCE -> task.setDone(true);
                case DAY -> task.setDate(task.getDate().plusDays(1));
                case WEEK -> task.setDate(task.getDate().plusWeeks(1));
                case MONTH -> task.setDate(task.getDate().plusMonths(1));
            }
            save(task);
        }
        for (ArchivedTask archive : archives) {
            if( archive.getRepeatable() != Repeatable.ONCE) {
                switch (archive.getRepeatable()) {
                    case DAY -> archive.setDate(archive.getDate().plusDays(1));
                    case WEEK -> archive.setDate(archive.getDate().plusWeeks(1));
                    case MONTH -> archive.setDate(archive.getDate().plusMonths(1));
                }
                Task archivedBefore = new Task(archive.getId(),
                        archive.getName(), archive.getDescription(),
                        archive.getDate(), archive.getPrior(), false,
                        archive.getCategory(), archive.getRepeatable(), archive.getClient());
                save(archivedBefore);
                archivedTaskRepository.deleteById(archive.getId());
            }
        }
    }
}
