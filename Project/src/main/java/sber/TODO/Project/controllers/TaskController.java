package sber.TODO.Project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sber.TODO.Project.entities.Task;
import sber.TODO.Project.services.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    public String showMainPage(Model model, @RequestParam(required = false, defaultValue = "all") String filter){
        if(filter.equals("all")) {
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
    public String save(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            taskService.save(task);
            return "redirect:/tasks/main";
        }
        return "/todo/create_task";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable long id, Model model){
        model.addAttribute("task", taskService.findOneById(id));
        return "todo/show";
    }

    @GetMapping("/status/{id}")
    public String show(@PathVariable long id){
        Task task = taskService.findOneById(id);
        task.setDone(!task.isDone());
        taskService.save(task);
        return "redirect:/tasks/main";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String search, @RequestParam(required = false) LocalDateTime date){
        List<Task> tasks = new ArrayList<Task>();
        if(date == null) {
            tasks = taskService.findAllByString(search);
        } else {
            tasks = taskService.findAllByStringAndDate(search, date);
        }
        model.addAttribute("tasks", tasks);
        return "todo/main";
    }


    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable long id, Model model){
        model.addAttribute("task", taskService.findOneById(id));
        return "todo/edit_task";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult, @PathVariable long id){
        if(!bindingResult.hasErrors()) {
            task.setId(id);
            taskService.save(task);
            return "redirect:/tasks/show/" + id;
        }
        return "todo/edit_task";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id){
        taskService.deleteById(id);
        return "redirect:/tasks/main";
    }
}
