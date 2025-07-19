package com.alfred.taskmanager.controller;

import com.alfred.taskmanager.dto.TaskRequestDto;
import com.alfred.taskmanager.dto.TaskResponseDto;
import com.alfred.taskmanager.enums.Priority;
import com.alfred.taskmanager.enums.TaskStatus;
import com.alfred.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateTask() throws Exception {
        TaskRequestDto request = new TaskRequestDto();
        request.setTitle("Test Task");
        request.setDescription("Test Description");
        request.setStatus(TaskStatus.PENDING);
        request.setPriority(Priority.MEDIUM);
        request.setDueDate(LocalDateTime.now().plusDays(2));

        TaskResponseDto response = new TaskResponseDto();
        response.setId(1L);
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setStatus(request.getStatus());
        response.setPriority(request.getPriority());
        response.setDueDate(request.getDueDate());
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        Mockito.when(taskService.createTask(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
    @Test
    public void testGetAllTasks() throws Exception {
        TaskResponseDto task1 = new TaskResponseDto();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Desc 1");
        task1.setStatus(TaskStatus.PENDING);
        task1.setPriority(Priority.HIGH);
        task1.setDueDate(LocalDateTime.now().plusDays(2));
        task1.setCreatedAt(LocalDateTime.now());
        task1.setUpdatedAt(LocalDateTime.now());

        TaskResponseDto task2 = new TaskResponseDto();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Desc 2");
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setPriority(Priority.LOW);
        task2.setDueDate(LocalDateTime.now().plusDays(3));
        task2.setCreatedAt(LocalDateTime.now());
        task2.setUpdatedAt(LocalDateTime.now());

        Mockito.when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }
    @Test
    public void testGetTaskById() throws Exception {
        Long taskId = 1L;

        TaskResponseDto responseDto = new TaskResponseDto();
        responseDto.setId(taskId);
        responseDto.setTitle("Test Task");
        responseDto.setDescription("Test Description");
        responseDto.setStatus(TaskStatus.IN_PROGRESS);
        responseDto.setPriority(Priority.MEDIUM);
        responseDto.setDueDate(LocalDateTime.now().plusDays(1));
        responseDto.setCreatedAt(LocalDateTime.now());
        responseDto.setUpdatedAt(LocalDateTime.now());

        Mockito.when(taskService.getTaskById(taskId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Long taskId = 1L;

        TaskRequestDto request = new TaskRequestDto();
        request.setTitle("Updated Task");
        request.setDescription("Updated Description");
        request.setStatus(TaskStatus.COMPLETED);
        request.setPriority(Priority.HIGH);
        request.setDueDate(LocalDateTime.now().plusDays(3));

        TaskResponseDto response = new TaskResponseDto();
        response.setId(taskId);
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setStatus(request.getStatus());
        response.setPriority(request.getPriority());
        response.setDueDate(request.getDueDate());
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        Mockito.when(taskService.updateTask(Mockito.eq(taskId), Mockito.any(TaskRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }
    @Test
    public void testDeleteTask() throws Exception {
        Long taskId = 1L;

        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isNoContent());

        Mockito.verify(taskService).deleteTask(taskId);
    }


}
