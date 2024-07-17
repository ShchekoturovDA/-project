package sber.TODO.Project.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sber.TODO.Project.entities.ArchivedTask;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.repositories.ArchivedTaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArchivedTaskService {
    private final ArchivedTaskRepository archivedTaskRepository;

    public ArchivedTaskService(ArchivedTaskRepository archivedtaskRepository) {
        this.archivedTaskRepository = archivedtaskRepository;
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

    public List<ArchivedTask> findAllByStringAndClient(String search, Client client) {
        return archivedTaskRepository.findAllByStringAndClient(search, client, Sort.by("date", "prior").ascending());
    }
}
