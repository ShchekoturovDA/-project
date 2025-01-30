package sber.TODO.Project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sber.TODO.Project.Category;
import sber.TODO.Project.entities.ArchivedTask;
import sber.TODO.Project.entities.Client;
import sber.TODO.Project.entities.Task;
import sber.TODO.Project.services.ArchivedTaskService;
import sber.TODO.Project.services.ClientService;
import sber.TODO.Project.services.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private ArchivedTaskService archivedTaskService;

    @RequestMapping("/create")
    public String showPageCreate(Model model) {
        model.addAttribute("task", new Task());
        return "/todo/create_task";
    }

    @RequestMapping("/main")
    public String showMainPage(Model model, @RequestParam(required = false, defaultValue = "all") String filter,
                               @RequestParam(required = false, defaultValue = "") String category, @AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientService.findByLogin(userDetails.getUsername());
        List<Task> tasks = taskService.findByClient(client);
        List<ArchivedTask> archives = archivedTaskService.findByClient(client);
        if (filter.equals("done")) {
            tasks = tasks.stream().filter(x -> x.isDone()).collect(Collectors.toList());
        } else if (filter.equals("in_process")) {
            tasks = tasks.stream().filter(x -> !x.isDone()).collect(Collectors.toList());
        }
        switch (category) {
            case "OTHER" :
                    tasks = tasks.stream().filter(x -> x.getCategory() == Category.OTHER).collect(Collectors.toList());
                    archives = archives.stream().filter(x -> x.getCategory() == Category.OTHER).collect(Collectors.toList());
                    break;
            case "WORK" :
                    tasks = tasks.stream().filter(x -> x.getCategory() == Category.WORK).collect(Collectors.toList());
                    archives = archives.stream().filter(x -> x.getCategory() == Category.WORK).collect(Collectors.toList());
                    break;
            case "HEALTH" :
                    tasks = tasks.stream().filter(x -> x.getCategory() == Category.HEALTH).collect(Collectors.toList());
                    archives = archives.stream().filter(x -> x.getCategory() == Category.HEALTH).collect(Collectors.toList());
                    break;
            case "REST" :
                    tasks = tasks.stream().filter(x -> x.getCategory() == Category.REST).collect(Collectors.toList());
                    archives = archives.stream().filter(x -> x.getCategory() == Category.HEALTH).collect(Collectors.toList());
                    break;
        }
        if (!filter.equals("archived")) {
            model.addAttribute("tasks", tasks);
        } else {
            model.addAttribute("archives", archives);
        }
        return "/todo/main";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        if (!bindingResult.hasErrors()) {
            Client client = clientService.findByLogin(userDetails.getUsername());
            task.setClient(client);
            taskService.save(task);
            return "redirect:/tasks/main";
        }
        return "/todo/create_task";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable long id, Model model) {
        if (!taskService.findOneById(id).isPresent()) {
            model.addAttribute("task", archivedTaskService.findOneById(id).get());
        } else {
            model.addAttribute("task", taskService.findOneById(id).get());
        }
        return "todo/show";
    }

    @GetMapping("/status/{id}")
    public String show(@PathVariable long id) {
        Task task = taskService.findOneById(id).get();
        task.setDone(!task.isDone());
        taskService.save(task);
        return "redirect:/tasks/main";
    }

    @GetMapping("/archive/{id}")
    public String archive(@PathVariable long id) {
        Task task = taskService.findOneById(id).get();
        ArchivedTask archivedTask = new ArchivedTask(task.getId(),
                task.getName(), task.getDescription(), task.getDate(),
                task.getPrior(), task.isDone(), task.getCategory(), task.getRepeatable(),
                task.getClient());
        archivedTaskService.save(archivedTask);
        return "redirect:/tasks/delete/" + id;
    }


    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String search, @RequestParam(required = false) LocalDateTime date, @AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientService.findByLogin(userDetails.getUsername());
        List<ArchivedTask> archivedTasks = archivedTaskService.findAllByStringAndClient(search, client);
        List<Task> tasks = taskService.findAllByStringAndUser(search, client);
        if (date != null) {
            archivedTasks = archivedTasks.stream().filter(x -> x.getDate().isBefore(date)).collect(Collectors.toList());
            tasks = tasks.stream().filter(x -> x.getDate().isBefore(date)).collect(Collectors.toList());
        }
        model.addAttribute("archives", archivedTasks);
        model.addAttribute("tasks", tasks);
        return "todo/main";
    }


    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable long id, Model model) {
        model.addAttribute("task", taskService.findOneById(id).get());
        return "todo/edit_task";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult, @PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (!bindingResult.hasErrors()) {
            task.setId(id);
            task.setClient(clientService.findByLogin(userDetails.getUsername()));
            taskService.save(task);
            return "redirect:/tasks/show/" + id;
        }
        return "todo/edit_task";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        if (taskService.findOneById(id).isPresent()) {
            taskService.deleteById(id);
        } else {
            archivedTaskService.deleteById(id);
        }
        return "redirect:/tasks/main";
    }

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void prolonging() {
        List<Task> tasks = taskService.findByDateBeforeAndDone(LocalDateTime.now(), false);
        List<ArchivedTask> archives = archivedTaskService.findByDateBefore(LocalDateTime.now());
        taskService.prolong(tasks, archives);
    }
}
