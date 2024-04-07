package edu.java.bot.service;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.dto.exception.BadRequestException;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotConsumerService {
    private final ApplicationConfig config;
    private final BotService botService;

    @KafkaListener(topics = "${app.kafka-config.topic-config.name}")
    public void listen(LinkUpdateRequest updateRequest) {
        try {
            botService.sendNotification(updateRequest);
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            log.error(e.getDescription());
        }
    }
}
