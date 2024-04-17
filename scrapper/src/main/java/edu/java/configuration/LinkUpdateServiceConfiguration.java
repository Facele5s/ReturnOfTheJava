package edu.java.configuration;

import edu.java.client.BotClient;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.service.LinkUpdateService;
import edu.java.service.ScrapperHttpUpdateSender;
import edu.java.service.ScrapperQueueProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class LinkUpdateServiceConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
    public LinkUpdateService scrapperQueueProducer(
        ApplicationConfig config, KafkaTemplate<Long,
        LinkUpdateRequest> kafkaTemplate
    ) {
        return new ScrapperQueueProducer(config, kafkaTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false", matchIfMissing = true)
    public LinkUpdateService scrapperHttpUpdateSender(BotClient botClient) {
        return new ScrapperHttpUpdateSender(botClient);
    }
}
