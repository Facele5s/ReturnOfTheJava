package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {
    @NotNull
    @Value("${app.telegram-token}")
    String telegramToken;

    @Bean
    TelegramBot getTelegramBot() {
        return new TelegramBot(telegramToken);
    }

    @Bean
    List<String> getLinksList() {
        return new ArrayList<>();
    }
}
