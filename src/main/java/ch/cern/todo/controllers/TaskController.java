package ch.cern.todo.controllers;

import ch.cern.todo.domain.entities.Task;
import ch.cern.todo.services.TaskService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tasks")
public class TaskController {


    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //CREATE
    //TODO: swith to dtos
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> createTask(@RequestBody Task createTask){
        taskService.createTask(createTask);
        return new ResponseEntity<>(createTask, HttpStatus.CREATED);
    }

    //READ
    //Get all tasks
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    //Get by id
    @GetMapping(value ="/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    //UPDATE
    @PutMapping(value ="/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        //Check if user entered an id in task body : if it's différent from given task id we return a bad call
        if (updatedTask.getTaskId() != null && !updatedTask.getTaskId().equals(taskId)) {
            return ResponseEntity.badRequest().build();
        }
        // Mettez à jour la tâche à l'aide du service
        Task updated = taskService.updateTask(taskId, updatedTask);
        return ResponseEntity.ok(updated);
    }

    //DELETE
    @DeleteMapping(value ="/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok("Task with ID " + taskId + " has been deleted successfully.");
        } catch (ResponseStatusException ex) {
            // Renvoyer une réponse HTTP appropriée avec le message d'erreur
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
    }
}
