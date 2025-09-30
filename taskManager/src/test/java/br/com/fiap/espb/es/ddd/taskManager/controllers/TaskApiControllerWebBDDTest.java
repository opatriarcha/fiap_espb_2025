package br.com.fiap.espb.es.ddd.taskManager.controllers;

import br.com.fiap.espb.es.ddd.taskManager.domainmodel.Task;
import br.com.fiap.espb.es.ddd.taskManager.domainmodel.TaskPriority;
import br.com.fiap.espb.es.ddd.taskManager.domainmodel.TaskStatus;
import br.com.fiap.espb.es.ddd.taskManager.dto.TaskDTO;
import br.com.fiap.espb.es.ddd.taskManager.service.TaskService;
import br.com.fiap.espb.es.ddd.taskManager.service.TaskServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * BDD Web test do TaskApiController (camada MVC isolada).
 * Não usa @MockBean (deprecated no Spring Boot 3.4+).
 * Registra um mock de TaskService como @Primary via @TestConfiguration.
 */
@WebMvcTest(TaskApiController.class)
class TaskApiControllerWebBDDTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @Autowired
    TaskServiceImpl taskService; // mock provido pela TestConfig

    @TestConfiguration
    static class TestConfig {
        @Bean @Primary
        TaskService taskService() {
            return Mockito.mock(TaskService.class);
        }
    }

    private Task sampleTask(Long id) {
        Task t = new Task();
        t.setId(id);
        t.setTitle("Estudar TDD");
        t.setDescription("Escrever testes BDD");
        t.setCreationDate(LocalDate.of(2025, 9, 1));
        t.setDueDate(LocalDate.of(2025, 10, 1));
        t.setStatus(TaskStatus.PENDING);
        t.setPriority(TaskPriority.HIGH);
        return t;
    }

    private TaskDTO sampleDTO(Long id) {
        return new TaskDTO(id, "Estudar TDD", "Escrever testes BDD",
                LocalDate.of(2025, 9, 1), null, LocalDate.of(2025, 10, 1),
                null, TaskStatus.PENDING, TaskPriority.HIGH);
    }

    @Nested
    @DisplayName("GET /api/tasks")
    class GetAll {

        @Test
        @DisplayName("Dado tarefas existentes, quando listar, então 200 e array DTO")
        void should_list_all_tasks() throws Exception {
            // Given
            var t1 = sampleTask(1L);
            var t2 = sampleTask(2L);
            BDDMockito.given(taskService.findAll()).willReturn(List.of(t1, t2));

            // When / Then
            mvc.perform(get("/api/tasks"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].title", is("Estudar TDD")));
        }
    }

    @Nested
    @DisplayName("GET /api/tasks/old")
    class GetAllOld {

        @Test
        @DisplayName("Dado tarefas existentes, quando listar formato antigo, então 200 e array de entidades")
        void should_list_all_tasks_old() throws Exception {
            var t1 = sampleTask(1L);
            var t2 = sampleTask(2L);
            BDDMockito.given(taskService.findAll()).willReturn(List.of(t1, t2));

            mvc.perform(get("/api/tasks/old"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)));
        }
    }

    @Nested
    @DisplayName("GET /api/tasks/{id}")
    class GetById {

        @Test
        @DisplayName("Dado ID existente, quando buscar, então 200 e DTO da tarefa")
        void should_return_200_when_found() throws Exception {
            var t = sampleTask(10L);
            BDDMockito.given(taskService.findById(10L)).willReturn(Optional.of(t));

            mvc.perform(get("/api/tasks/{id}", 10L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(10)))
                    .andExpect(jsonPath("$.title", is("Estudar TDD")));
        }

        @Test
        @DisplayName("Dado ID inexistente, quando buscar, então 5xx (bug: usa Optional.get())")
        void should_return_5xx_when_not_found_due_to_get_bug() throws Exception {
            BDDMockito.given(taskService.findById(999L)).willReturn(Optional.empty());

            mvc.perform(get("/api/tasks/{id}", 999L))
                    .andExpect(status().is5xxServerError()); // evidência do bug atual
        }
    }

    @Nested
    @DisplayName("POST /api/tasks")
    class Create {

        @Test
        @DisplayName("Dado payload válido, quando criar, então 200 e retorna DTO salvo")
        void should_create_and_return_200() throws Exception {
            var req = sampleDTO(null);

            var saved = sampleTask(100L);
            BDDMockito.given(taskService.save(any(Task.class))).willReturn(saved);

            mvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(req)))
                    .andExpect(status().isOk()) // controlador retorna ok(), não 201
                    .andExpect(jsonPath("$.id", is(100)))
                    .andExpect(jsonPath("$.title", is("Estudar TDD")));
        }
    }

    @Nested
    @DisplayName("PUT /api/tasks/{id}")
    class Update {

        @Test
        @DisplayName("Dado payload válido, quando atualizar existente, então 200 e retorna DTO")
        void should_update_and_return_200() throws Exception {
            var req = sampleDTO(null);
            var updated = sampleTask(200L);
            updated.setTitle("Estudar BDD + Mockito");

            BDDMockito.given(taskService.save(any(Task.class))).willReturn(updated);

            mvc.perform(put("/api/tasks/{id}", 200L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(200)))
                    .andExpect(jsonPath("$.title", is("Estudar BDD + Mockito")));
        }

        @Test
        @DisplayName("Dado service salvando null (não encontrado), quando atualizar, então 404")
        void should_404_when_service_returns_null() throws Exception {
            var req = sampleDTO(null);
            BDDMockito.given(taskService.save(any(Task.class))).willReturn(null);

            mvc.perform(put("/api/tasks/{id}", 321L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(req)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE /api/tasks/{id}")
    class Delete {

        @Test
        @DisplayName("Dado ID válido, quando deletar, então 204")
        void should_delete_and_return_204() throws Exception {
            mvc.perform(delete("/api/tasks/{id}", 777L))
                    .andExpect(status().isNoContent());

            BDDMockito.then(taskService).should().deleteById(777L);
        }
    }
}
