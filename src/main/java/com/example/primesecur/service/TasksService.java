package com.example.primesecur.service;


import com.example.primesecur.model.Tasks;
import com.example.primesecur.repository.TasksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TasksService {

    Tasks addTask(Tasks tasks);
    List<Tasks> getTasks();
    Tasks getTask(Long id);
    void deleteTask(Tasks tasks);
    Tasks updateTask(Tasks tasks);

}
