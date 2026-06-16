package com.kata.todo.cucumber.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.todo.entity.TaskEntity;
import com.kata.todo.repository.TaskRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class TaskStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult lastResult;
    private Long lastTaskId;

    @Before
    public void setUp() {
        taskRepository.deleteAll();
        lastResult = null;
        lastTaskId = null;
    }

    // -------------------------------------------------------------------------
    // Given
    // -------------------------------------------------------------------------

    @Given("la base de données est vide")
    public void laBdEstVide() {
        taskRepository.deleteAll();
    }

    @Given("une tâche avec le libellé {string} existe")
    public void uneTacheExiste(String label) {
        TaskEntity task = taskRepository.save(new TaskEntity(null, label, false, null, null));
        lastTaskId = task.getId();
    }

    @Given("{int} tâches existent")
    public void nTachesExistent(int count) {
        for (int i = 1; i <= count; i++) {
            TaskEntity task = taskRepository.save(new TaskEntity(null, "Tâche " + i, false, null, null));
            lastTaskId = task.getId();
        }
    }

    @Given("{int} tâches non complétées existent")
    public void nTachesNonCompleteesExistent(int count) {
        for (int i = 1; i <= count; i++) {
            taskRepository.save(new TaskEntity(null, "En attente " + i, false, null, null));
        }
    }

    @Given("{int} tâches complétées existent")
    public void nTachesCompleteesExistent(int count) {
        for (int i = 1; i <= count; i++) {
            taskRepository.save(new TaskEntity(null, "Complétée " + i, true, null, null));
        }
    }

    // -------------------------------------------------------------------------
    // When
    // -------------------------------------------------------------------------

    @When("je crée une tâche avec le libellé {string}")
    public void jeCreeLaTache(String label) throws Exception {
        lastResult = mockMvc.perform(post("/tasks")
                        .param("label", label)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @When("je récupère toutes les tâches")
    public void jeRecupereToutesLesTaches() throws Exception {
        lastResult = mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @When("je récupère la tâche par son ID")
    public void jeRecupereLaTacheParSonId() throws Exception {
        lastResult = mockMvc.perform(get("/tasks/{id}", lastTaskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @When("je récupère la tâche avec l'ID {long}")
    public void jeRecupereLaTacheAvecId(long id) throws Exception {
        lastResult = mockMvc.perform(get("/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @When("je mets à jour le statut de la tâche à {string}")
    public void jeMetsAJourLeStatut(String complete) throws Exception {
        lastResult = mockMvc.perform(put("/tasks/{id}/status", lastTaskId)
                        .param("complete", complete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @When("je mets à jour le statut de la tâche avec l'ID {long} à {string}")
    public void jeMetsAJourLeStatutAvecId(long id, String complete) throws Exception {
        lastResult = mockMvc.perform(put("/tasks/{id}/status", id)
                        .param("complete", complete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @When("je supprime la tâche")
    public void jeSupprimeLaTache() throws Exception {
        lastResult = mockMvc.perform(delete("/tasks/{id}", lastTaskId))
                .andDo(print())
                .andReturn();
    }

    @When("je supprime la tâche avec l'ID {long}")
    public void jeSupprimeLaTacheAvecId(long id) throws Exception {
        lastResult = mockMvc.perform(delete("/tasks/{id}", id))
                .andDo(print())
                .andReturn();
    }

    @When("je récupère les tâches paginées page {int} taille {int}")
    public void jeRecupereLesTasksPaginees(int pageNo, int pageSize) throws Exception {
        lastResult = mockMvc.perform(get("/tasks/pagination")
                        .param("pageNo", String.valueOf(pageNo))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @When("je récupère les tâches en attente page {int} taille {int}")
    public void jeRecupereLesTasksEnAttente(int pageNo, int pageSize) throws Exception {
        lastResult = mockMvc.perform(get("/tasks/pending")
                        .param("pageNo", String.valueOf(pageNo))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @When("je récupère les tâches paginées triées par {string}")
    public void jeRecupereLesTasksTriesPar(String sortBy) throws Exception {
        lastResult = mockMvc.perform(get("/tasks/pagination")
                        .param("sortBy", sortBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    // -------------------------------------------------------------------------
    // Then / And
    // -------------------------------------------------------------------------

    @Then("la réponse a le statut {int}")
    public void laReponseALeStatut(int expectedStatus) {
        assertThat(lastResult.getResponse().getStatus()).isEqualTo(expectedStatus);
    }

    @And("la tâche a le libellé {string}")
    public void laTacheALeLibelle(String expectedLabel) throws Exception {
        JsonNode body = parseBody();
        assertThat(body.get("label").asText()).isEqualTo(expectedLabel);
    }

    @And("la tâche est non complétée")
    public void laTacheEstNonCompletee() throws Exception {
        JsonNode body = parseBody();
        assertThat(body.get("complete").asBoolean()).isFalse();
    }

    @And("la tâche est complétée")
    public void laTacheEstCompletee() throws Exception {
        JsonNode body = parseBody();
        assertThat(body.get("complete").asBoolean()).isTrue();
    }

    @And("la liste contient {int} tâches")
    public void laListeContient(int expectedCount) throws Exception {
        JsonNode body = parseBody();
        assertThat(body.isArray()).isTrue();
        assertThat(body.size()).isEqualTo(expectedCount);
    }

    @And("la page contient {int} tâches")
    public void laPageContient(int expectedCount) throws Exception {
        JsonNode body = parseBody();
        assertThat(body.get("content").size()).isEqualTo(expectedCount);
    }

    @And("le nombre total d'éléments est {int}")
    public void leNombreTotalDElementsEst(int expectedTotal) throws Exception {
        JsonNode body = parseBody();
        assertThat(body.get("totalElements").asLong()).isEqualTo(expectedTotal);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private JsonNode parseBody() throws Exception {
        return objectMapper.readTree(lastResult.getResponse().getContentAsString());
    }
}
