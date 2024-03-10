package edu.java.scrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ScrapperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /*@Test
    @DisplayName("Проверка GET /links")
    public void testGet() throws Exception {
        mockMvc.perform(get("/links")
            .header("Tg-Chat-Id", 10)
            .contentType("application/json"))
            .andExpect(status().isOk());
    }*/
}
