package ch.cern.todo.domain.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Task {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String taskName;

    private String taskDescription;

    private Timestamp deadline;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private TaskCategory category;

    // GETTERS & SETTERS
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }
}
