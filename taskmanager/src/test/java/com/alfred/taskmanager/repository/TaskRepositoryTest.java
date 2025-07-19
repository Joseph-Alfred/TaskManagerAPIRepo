package com.alfred.taskmanager.repository;

import com.alfred.taskmanager.entity.Task;
import com.alfred.taskmanager.enums.Priority;
import com.alfred.taskmanager.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveAndFindById() {
        Task task = new Task();
        task.setTitle("Repo Test Task");
        task.setDescription("Testing repository");
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(Priority.MEDIUM);
        task.setDueDate(LocalDateTime.now().plusDays(3));

        Task savedTask = taskRepository.save(task);

        Optional<Task> found = taskRepository.findById(savedTask.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Repo Test Task");
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task();
        task.setTitle("Delete Test");
        task.setDescription("To be deleted");
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(Priority.LOW);
        task.setDueDate(LocalDateTime.now().plusDays(1));

        Task savedTask = taskRepository.save(task);
        Long id = savedTask.getId();

        taskRepository.deleteById(id);

        Optional<Task> result = taskRepository.findById(id);
        assertThat(result).isNotPresent();
    }
}

