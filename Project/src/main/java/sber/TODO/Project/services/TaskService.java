package sber.TODO.Project.services;

import org.springframework.stereotype.Service;
import sber.TODO.Project.entities.Task;
import sber.TODO.Project.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }


    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public List<Task> findByDone(boolean done){
        return taskRepository.findAllByDone(done);
    }

    public Task findOneById(long id) {
        return taskRepository.findById(id).get();
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findAllByString(String search) {
        return taskRepository.findAllByString(search);
    }

    public List<Task> findAllByStringAndDate(String search, LocalDateTime date) {
        return taskRepository.findAllByStringAndDate(search, date);
    }
}
