package ch.cern.todo.config;


import ch.cern.todo.domain.entities.Task;
import ch.cern.todo.domain.entities.TaskCategory;
import ch.cern.todo.domain.repository.TaskCategoryRepository;
import ch.cern.todo.domain.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;

@Configuration
public class DataSampleInitConfig {

    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskRepository taskRepository;

    public DataSampleInitConfig(TaskCategoryRepository taskCategoryRepository, TaskRepository taskRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void initData() {

        TaskCategory work = new TaskCategory();
        work.setCategoryId(1L);
        work.setCategoryName("Work");
        work.setCategoryDescription("all tasks with a professional/work goal");
        taskCategoryRepository.save(work);

        TaskCategory personal = new TaskCategory();
        personal.setCategoryId(2L);
        personal.setCategoryName("Personal");
        personal.setCategoryDescription("all tasks with a personal goal: hobbies, sport, family etc");
        taskCategoryRepository.save(personal);

        Task task1 = new Task();
        task1.setTaskId(1L);
        task1.setTaskName("Todo web app");
        task1.setTaskDescription("TODO REST API with CRUD: technical assignment from CERN");
        task1.setDeadline(Timestamp.valueOf("2024-04-11 12:00:00"));
        task1.setCategory(work);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTaskId(2L);
        task2.setTaskName("Moto Lesson");
        task2.setTaskDescription("First lesson, bring all papers for registration");
        task2.setDeadline(Timestamp.valueOf("2024-04-15 18:00:00"));
        task2.setCategory(personal);
        taskRepository.save(task2);


    }

}
