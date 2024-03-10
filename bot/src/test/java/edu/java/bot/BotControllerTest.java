package edu.java.bot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Update sending")
    public void updatesTest() throws Exception {
        mockMvc.perform(post("/updates")
            .contentType("application/json")
                .content("{\"id\":100500,\"url\":\"http://google.com\",\"description\":\"test\",\"tgChatIds\":[0,0]}"))
            .andExpect(status().isOk());
    }
}
