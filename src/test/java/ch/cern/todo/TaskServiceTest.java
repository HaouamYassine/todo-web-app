package ch.cern.todo;

import ch.cern.todo.domain.entities.Task;
import ch.cern.todo.domain.entities.TaskCategory;
import ch.cern.todo.domain.repository.TaskCategoryRepository;
import ch.cern.todo.domain.repository.TaskRepository;
import ch.cern.todo.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskCategoryRepository taskCategoryRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_CategoryNotFound_ThrowsNoSuchElementException() {
        Task task = new Task();
        task.setTaskId(10L);
        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setCategoryId(10L);
        task.setCategory(taskCategory);

        // Mock
        when(taskCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Verify NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> taskService.createTask(task));

        // Verify save is not called
        verify(taskRepository, never()).save(any());
    }

    @Test
    void createTask_CategoryFound_SavesTask() {
        Task task = new Task();
        task.setTaskId(10L);
        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setCategoryId(10L);
        task.setCategory(taskCategory);

        // Mock
        when(taskCategoryRepository.findById(anyLong())).thenReturn(Optional.of(taskCategory));

        // Call createTask
        taskService.createTask(task);

        // Verify save is called
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void createTask_NoCategory_SavesTask() {
        Task task = new Task();
        task.setTaskId(10L);

        // Call createTask
        taskService.createTask(task);

        // Verify save is called
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void getAllTasks_ReturnsTaskList() {
        // Mock
        when(taskRepository.findAll()).thenReturn(Arrays.asList(new Task(), new Task()));
        // Call getAllTasks
        List<Task> tasks = taskService.getAllTasks();
        // Verify findAll() is called
        verify(taskRepository, times(1)).findAll();
        // Assert size
        assertEquals(2, tasks.size());
    }

    @Test
    void getTaskById_TaskExists_ReturnsTask() {
        Long taskId = 10L;
        Task task = new Task();
        task.setTaskId(taskId);
        // Mock
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        // Call getTaskById
        Optional<Task> returnedTask = taskService.getTaskById(taskId);
        // Verify findById() is called
        verify(taskRepository, times(1)).findById(taskId);
        // Assert
        assertTrue(returnedTask.isPresent());
        assertEquals(taskId, returnedTask.get().getTaskId());
    }

    @Test
    void getTaskById_TaskDoesNotExist_ReturnsEmpty() {
        Long taskId = 10L;
        // Mock
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        // Call getTaskById
        Optional<Task> returnedTask = taskService.getTaskById(taskId);
        // VerifyfindById() is called
        verify(taskRepository, times(1)).findById(taskId);
        // Assert
        assertFalse(returnedTask.isPresent());
    }

    @Test
    void updateTask_TaskDoesNotExist_ThrowsNoSuchElementException() {
        Long taskId = 10L;
        Task updatedTask = new Task();
        updatedTask.setTaskId(taskId);
        // Mock
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        // Verify  NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> taskService.updateTask(taskId, updatedTask));
        // Verify save() is not called
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_TaskExists_UpdatesAndSavesTask() {
        Long taskId = 10L;
        Task existingTask = new Task();
        existingTask.setTaskId(taskId);
        Task updatedTask = new Task();
        updatedTask.setTaskName("Updated name");
        updatedTask.setTaskDescription("Updated description");
        updatedTask.setDeadline(Timestamp.valueOf(LocalDateTime.now()));
        TaskCategory updatedCategory = new TaskCategory();
        updatedCategory.setCategoryId(20L);
        updatedTask.setCategory(updatedCategory);

        // Mock
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArguments()[0]);

        // Call updateTask
        Task returnedTask = taskService.updateTask(taskId, updatedTask);

        // Verify save() is called
        verify(taskRepository, times(1)).save(any(Task.class));

        // Assert
        assertEquals("Updated name", returnedTask.getTaskName());
        assertEquals("Updated description", returnedTask.getTaskDescription());
        assertEquals(updatedTask.getDeadline(), returnedTask.getDeadline());
        assertEquals(updatedCategory.getCategoryId(), returnedTask.getCategory().getCategoryId());
    }

    @Test
    void deleteTask_TaskExists_DeletesTask() {
        Long taskId = 10L;
        Task existingTask = new Task();
        existingTask.setTaskId(taskId);
        // Mock
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        // Call deleteTask
        taskService.deleteTask(taskId);
        // Verify delete() is called
        verify(taskRepository, times(1)).delete(existingTask);
    }

    @Test
    void deleteTask_TaskDoesNotExist_ThrowsResponseStatusException() {
        Long taskId = 10L;
        // Mock
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        // Verify ResponseStatusException
        assertThrows(ResponseStatusException.class, () -> taskService.deleteTask(taskId));
        // Verify delete() is not called
        verify(taskRepository, never()).delete(any(Task.class));
    }

}
