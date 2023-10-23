package com.example.primesecur.service.impl;

import com.example.primesecur.model.Tasks;
import com.example.primesecur.model.User;
import com.example.primesecur.repository.TasksRepository;
import com.example.primesecur.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TasksService {
    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public Tasks addTask(Tasks tasks) {
        return tasksRepository.save(tasks);
    }

    @Override
    public List<Tasks> getTasks(User user) {
        return tasksRepository.findByUser(user);
    }

    @Override
    public Tasks getTask(Long id) {
        return tasksRepository.findByIdAndDateGreaterThan(id, LocalDate.ofEpochDay(0));
    }

    @Override
    public void deleteTask(Tasks tasks) {
        tasksRepository.delete(tasks);
    }

    @Override
    public Tasks updateTask(Tasks tasks) {
        return tasksRepository.save(tasks);
    }

}
