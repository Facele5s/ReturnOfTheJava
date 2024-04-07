package edu.java.service;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer implements LinkUpdateService {
    private final ApplicationConfig config;
    private final KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate;

    @Override
    public void sendUpdate(LinkUpdateRequest updateRequest) {
        String topicName = config.kafkaConfig().topicConfig().name();

        kafkaTemplate.send(topicName, updateRequest.id(), updateRequest);
    }
}
