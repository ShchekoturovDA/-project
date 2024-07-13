package sber.TODO.Project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sber.TODO.Project.entities.Task;
import sber.TODO.Project.services.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/create")
    public String showPageCreate(){
        return "/todo/create_task";
    }

    @PostMapping("/add")
    public String save(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("date") String date){

        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setDate(LocalDateTime.parse(date));
        taskService.save(task);
        return "/todo/main";
    }

}
