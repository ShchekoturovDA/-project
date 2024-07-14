package sber.TODO.Project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String showPageCreate(Model model){
        model.addAttribute("task", new Task());
        return "/todo/create_task";
    }

    @RequestMapping("/main")
    public String showMainPage(Model model,@RequestParam(required = false, defaultValue = "") String filter){
        if(filter.equals("")) {
            model.addAttribute("tasks", taskService.findAll());
        } else if (filter.equals("done")) {
            model.addAttribute("tasks", taskService.findByDone(true));
        } else if (filter.equals("in_process")) {
            model.addAttribute("tasks", taskService.findByDone(false));
        } else {
            model.addAttribute("tasks", taskService.findByDone(false));
        }
        return "/todo/main";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("task") Task task){
        taskService.save(task);
        return "redirect:/tasks/main";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable long id, Model model){
        model.addAttribute("task", taskService.findOneById(id));
        return "todo/show";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable long id, Model model){
        model.addAttribute("task", taskService.findOneById(id));
        return "todo/edit_task";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute("task") Task task, @PathVariable long id){
        task.setId(id);
        taskService.save(task);
        return "redirect:/tasks/show/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id){
        taskService.deleteById(id);
        return "redirect:/tasks/main";
    }
}
