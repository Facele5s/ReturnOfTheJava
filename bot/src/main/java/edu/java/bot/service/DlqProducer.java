package edu.java.bot.service;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DlqProducer {
    private final ApplicationConfig config;
    private final KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate;

    public void send(LinkUpdateRequest updateRequest) {
        kafkaTemplate.send(config.kafkaConfig().topicConfig().name() + "dlq", updateRequest);
    }
}
