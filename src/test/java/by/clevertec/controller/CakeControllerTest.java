package by.clevertec.controller;

import by.clevertec.domain.Cake;
import by.clevertec.service.CakeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.TestData;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CakeController.class)
class CakeControllerTest {

    @MockBean
    private CakeService cakeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFindAll() throws Exception {
        //given
        when(cakeService.findCakes())
                .thenReturn(List.of(TestData.getCake(), TestData.getCake()));

        //when, then
        mockMvc.perform(get("/api/v1/cakes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldFindCakeById() throws Exception {
        //given
        Cake cake = TestData.getCake();
        when(cakeService.findCakeById(cake.getId()))
                .thenReturn(cake);

        //when, then
        mockMvc.perform(get("/api/v1/cake/" + cake.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cake.getId().toString()))
                .andExpect(jsonPath("$.title").value(cake.getTitle()))
                .andExpect(jsonPath("$.cakeType").value(cake.getCakeType().toString()))
                .andExpect(jsonPath("$.expiredPeriod").value(cake.getExpiredPeriod().toString()));
    }

    @Test
    void shouldCreateCake() throws Exception {
        //given
        Cake cake = TestData.getCake();
        when(cakeService.createCake(cake))
                .thenReturn(cake);

        //when, then
        mockMvc.perform(post("/api/v1/cake")
                        .content(TestData.getObjectMapper()
                                .writeValueAsString(cake))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cake.getId().toString()));
    }

    @Test
    void shouldUpdateCake() throws Exception {
        //given
        Cake cake = TestData.getCake();
        when(cakeService.updateCake(cake.getId(), cake))
                .thenReturn(cake);

        //when, then
        mockMvc.perform(put("/api/v1/cake/" + cake.getId())
                        .content(TestData.getObjectMapper()
                                .writeValueAsString(cake))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(cake.getTitle()));
    }

    @Test
    void shouldDeleteCake() throws Exception {
        mockMvc.perform(delete("/api/v1/cake/" + UUID.randomUUID()))
                .andExpect(status().isOk());
    }
}