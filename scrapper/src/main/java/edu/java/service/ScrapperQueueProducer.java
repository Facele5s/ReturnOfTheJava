package edu.java.service;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    private final ApplicationConfig config;
    private final KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate;

    public void send(LinkUpdateRequest updateRequest) {

    }
}
