package com.example.primesecur.controller;

import com.example.primesecur.model.Tasks;
import com.example.primesecur.model.User;
import com.example.primesecur.service.TasksService;
import com.example.primesecur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TasksController {
    @Autowired
    private TasksService tasksService;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/")
    public String indexPage( Model model){
        List<Tasks> tasks = tasksService.getTasks();
        model.addAttribute("tasks", tasks);
        return "index";
    }

   // @PreAuthorize("hasRole('ROLE_USER') and #tasks.user.email == authentication.name")
//   @PreAuthorize("isAuthenticated()")
   @PreAuthorize("isAuthenticated() and @userService.canUserAccessTask(authentication.principal, #id)")
    @GetMapping(value = "/details/{idsh}")
    public String details(Model model, @PathVariable(name = "idsh" ) Long id){
        Tasks tasks = tasksService.getTask(id);
        model.addAttribute("tasks", tasks);

        return "details";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/add-task")
    public String addTask(){
        return "addtask";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/add-task")
    public String addTask(@RequestParam(name = "task_date") LocalDate date,
                         @RequestParam(name = "task_description") String description){


        User currentUser = userService.getCurrentSessionUser();

        if (currentUser != null) {
            Tasks task = new Tasks();
            task.setDate(date);
            task.setDescription(description);

            task.setUser(currentUser);

            tasksService.addTask(task);

            return "redirect:/";
        } else {
            return "redirect:/signin";
        }
    }

    @PreAuthorize("isAuthenticated() and @userService.canUserAccessTask(authentication.principal, #id)")
    @GetMapping(value = "/edit/{idsh}")
    public String edit(Model model, @PathVariable(name = "idsh" ) Long id){
        Tasks tasks = tasksService.getTask(id);
        model.addAttribute("tasks", tasks);
        return "edit";
    }

    @PreAuthorize("isAuthenticated() and @userService.canUserAccessTask(authentication.principal, #id)")
    @PostMapping(value = "/save")
    public String save(@RequestParam(name = "task_id") Long id,
                          @RequestParam(name = "task_date") LocalDate date,
                          @RequestParam(name = "task_description") String description){

        User currentUser = userService.getCurrentSessionUser();

        if (currentUser != null) {

            Tasks tasks = tasksService.getTask(id);
            if (tasks!=null){
                tasks.setDate(date);
                tasks.setDescription(description);
                tasks.setUser(currentUser);
                tasksService.updateTask(tasks);
            }

            return "redirect:/";
        } else {
            return "redirect:/signin";
        }

    }

    @PreAuthorize("isAuthenticated() and @userService.canUserAccessTask(authentication.principal, #id)")
    @PostMapping(value = "/delete")
    public String delete(@RequestParam(name = "task_id") Long id){
        Tasks tasks = tasksService.getTask(id);
        if (tasks!=null){
            tasksService.deleteTask(tasks);
        }
        return "redirect:/";
    }



}
