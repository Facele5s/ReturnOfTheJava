package edu.java.scrapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class ScrapperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Chat registration")
    public void chatRegisterTest() throws Exception {
        mockMvc.perform(post("/tg-chat/1")
                .contentType("application/json"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Chat deleting")
    public void chatDeleteTest() throws Exception {
        mockMvc.perform(delete("/tg-chat/2")
                .contentType("application/json"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Link adding")
    public void linkAddTest() throws Exception {
        String response = "{\n" +
            "  \"link\": \"string\"\n" +
            "}";

        mockMvc.perform(post("/links")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Tg-Chat-id", 3)
                .content(response))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Link deleting")
    public void linkDeleteTest() throws Exception {
        String response = "{\n" +
            "  \"link\": \"string\"\n" +
            "}";

        mockMvc.perform(post("/links")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Tg-Chat-id", 4)
                .content(response))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Links getting")
    public void linkGetTest() throws Exception {
        mockMvc.perform(post("/tg-chat/5")
                .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        mockMvc.perform(get("/links")
                .header("Tg-Chat-id", 5)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
