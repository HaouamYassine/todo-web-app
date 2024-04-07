package ch.cern.todo.domain.repository;

import ch.cern.todo.domain.entities.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCategoryRepository  extends JpaRepository<TaskCategory, Long> {
}
