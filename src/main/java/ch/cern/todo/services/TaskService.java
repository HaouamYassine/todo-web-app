package ch.cern.todo.services;

import ch.cern.todo.domain.entities.Task;
import ch.cern.todo.domain.entities.TaskCategory;
import ch.cern.todo.domain.repository.TaskCategoryRepository;
import ch.cern.todo.domain.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;

    public TaskService(TaskRepository taskRepository, TaskCategoryRepository taskCategoryRepository) {
        this.taskRepository = taskRepository;
        this.taskCategoryRepository = taskCategoryRepository;
    }


    public void createTask(Task task) {
       try {
           if (task.getCategory() != null) {
               // Récupérer la catégorie correspondant à l'ID donné
               TaskCategory category = taskCategoryRepository.findById(task.getCategory().getCategoryId())
                       .orElseThrow(() -> new NoSuchElementException("Category not found"));
               // Associer la catégorie à la tâche
               task.setCategory(category);
           }

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

    //Update
    public Task updateTask(Long taskId, Task updatedTask) {
        //Check if the given task exist in our DB
        Optional<Task> taskToUpdateOptional = taskRepository.findById(taskId);
        if (taskToUpdateOptional.isPresent()) {
            Task taskToUpdate = taskToUpdateOptional.get();
            // Update task
            taskToUpdate.setTaskName(updatedTask.getTaskName());
            taskToUpdate.setTaskDescription(updatedTask.getTaskDescription());
            taskToUpdate.setDeadline(updatedTask.getDeadline());
            taskToUpdate.setCategory(updatedTask.getCategory());
            // Saving
            return taskRepository.save(taskToUpdate);

        }
        else {
            throw new NoSuchElementException("There is no task with ID "+ updatedTask.getTaskId());
        }
    }

    public void deleteTask(Long taskId) {
        Optional<Task> taskToDelete = taskRepository.findById(taskId);
        if (taskToDelete.isPresent()) {
            taskRepository.delete(taskToDelete.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You can't delete the task: There is no task with ID " + taskId);
        }
    }


}
