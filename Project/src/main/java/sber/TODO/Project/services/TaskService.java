package sber.TODO.Project.services;

import org.springframework.stereotype.Service;
import sber.TODO.Project.entities.Task;
import sber.TODO.Project.repositories.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }


    public Task save(Task task) {
        return taskRepository.save(task);
    }
}
