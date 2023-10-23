package com.example.primesecur.service;


import com.example.primesecur.model.Tasks;
import com.example.primesecur.model.User;

import java.util.List;

public interface TasksService {

    Tasks addTask(Tasks tasks);
    List<Tasks> getTasks(User user);
    Tasks getTask(Long id);
    void deleteTask(Tasks tasks);
    Tasks updateTask(Tasks tasks);

}
