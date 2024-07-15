package sber.TODO.Project.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sber.TODO.Project.entities.ArchivedTask;
import sber.TODO.Project.entities.Task;
import sber.TODO.Project.repositories.ArchivedTaskRepository;
import sber.TODO.Project.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArchivedTaskService {
    private final ArchivedTaskRepository archivedTaskRepository;

    public ArchivedTaskService(ArchivedTaskRepository archivedtaskRepository){
        this.archivedTaskRepository = archivedtaskRepository;
    }

    public List<ArchivedTask> findAllByString(String search) {
        return  archivedTaskRepository.findAllByString(search, Sort.by("date", "prior").ascending());
    }

    public List<ArchivedTask> findAllByStringAndDate(String search, LocalDateTime date) {
        return archivedTaskRepository.findAllByStringAndDate(search, date, Sort.by("date", "prior").ascending());
    }

    public void save(ArchivedTask archivedTask) {
        archivedTaskRepository.save(archivedTask);
    }

    public Optional<ArchivedTask> findOneById(long id) {
        return archivedTaskRepository.findById(id);
    }

    public void deleteById(long id) {
        archivedTaskRepository.deleteById(id);
    }
}
