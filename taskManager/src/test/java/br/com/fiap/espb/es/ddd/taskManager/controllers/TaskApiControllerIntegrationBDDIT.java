package br.com.fiap.espb.es.ddd.taskManager.controllers;

import br.com.fiap.espb.es.ddd.taskManager.domainmodel.TaskStatus;
import br.com.fiap.espb.es.ddd.taskManager.dto.TaskDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * BDD Integração do TaskApiController.
 * Usa o contexto real (sem mock de service), persistindo em DB de teste.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TaskApiControllerIntegrationBDDIT {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    private TaskDTO req(String title) {
        return new TaskDTO(null, title, "Escrever testes",
                LocalDate.of(2025, 9, 1), null, LocalDate.of(2025, 10, 1),
                null, TaskStatus.PENDING,
                br.com.fiap.espb.es.ddd.taskManager.domainmodel.TaskPriority.MEDIUM);
    }

    @Nested
    @DisplayName("Fluxo CRUD completo via HTTP")
    class CrudFlow {

        @Test
        @DisplayName("Dado criação por POST, quando buscar por ID/atualizar/deletar, então respostas coerentes")
        void fullCrudFlow() throws Exception {
            // POST cria
            var postResult = mvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(req("Primeira tarefa"))))
                    .andExpect(status().isOk()) // controlador usa ok()
                    .andExpect(jsonPath("$.id", notNullValue()))
                    .andReturn();

            var created = mapper.readValue(
                    postResult.getResponse().getContentAsString(), TaskDTO.class);

            // GET all
            mvc.perform(get("/api/tasks"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", not(empty())));

            // GET by id (⚠️ se remover depois, esse endpoint dá 5xx por causa do .get())
            mvc.perform(get("/api/tasks/{id}", created.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(created.getId().intValue())))
                    .andExpect(jsonPath("$.title", is("Primeira tarefa")));

            // PUT update
            var update = new TaskDTO(null, "Tarefa atualizada", "Atualizar",
                    LocalDate.of(2025, 9, 2), null, LocalDate.of(2025, 10, 2),
                    null, br.com.fiap.espb.es.ddd.taskManager.domainmodel.TaskStatus.IN_PROGRESS,
                    br.com.fiap.espb.es.ddd.taskManager.domainmodel.TaskPriority.HIGH);

            mvc.perform(put("/api/tasks/{id}", created.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(update)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", is("Tarefa atualizada")));

            // DELETE
            mvc.perform(delete("/api/tasks/{id}", created.getId()))
                    .andExpect(status().isNoContent());

            // GET by id após exclusão → hoje dá 5xx por causa do Optional.get()
            mvc.perform(get("/api/tasks/{id}", created.getId()))
                    .andExpect(status().is5xxServerError()); // evidência do bug atual
        }
    }

    @Nested
    @DisplayName("Listagem / formato antigo")
    class Listing {

        @Test
        @DisplayName("Dado algumas tarefas, quando GET /api/tasks e /api/tasks/old, então 200 e itens")
        void list_endpoints() throws Exception {
            // cria duas tarefas
            mvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(req("Tarefa A"))))
                    .andExpect(status().isOk());

            mvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(req("Tarefa B"))))
                    .andExpect(status().isOk());

            // GET /api/tasks (DTO)
            var res1 = mvc.perform(get("/api/tasks"))
                    .andExpect(status().isOk())
                    .andReturn();

            List<TaskDTO> list = mapper.readValue(
                    res1.getResponse().getContentAsString(),
                    new TypeReference<List<TaskDTO>>() {}
            );
            Assertions.assertTrue(list.size() >= 2);

            // GET /api/tasks/old (entidade)
            mvc.perform(get("/api/tasks/old"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", not(empty())));
        }
    }
}
