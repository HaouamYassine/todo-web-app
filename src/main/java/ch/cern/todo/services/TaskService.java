package ch.cern.todo.services;

import ch.cern.todo.domain.entities.Task;
import ch.cern.todo.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public void createTask(Task task) {
       try {
           taskRepository.save(task);
       }
       catch (Exception e) {
           System.out.println("Task creation failed");
       }
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long taskId){
        return taskRepository.findById(taskId);
    }

}
