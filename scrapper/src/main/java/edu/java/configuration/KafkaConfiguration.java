package edu.java.configuration;

import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final ApplicationConfig config;

    private final String bootstrapServers = config.kafkaConfig().bootstrapServers();
    private final String topicName = config.kafkaConfig().topicConfig().name();
    private final Integer partitions = config.kafkaConfig().topicConfig().partitions();
    private final Integer replicas = config.kafkaConfig().topicConfig().replicas();

    @Bean
    public NewTopic topic() {
        return TopicBuilder
            .name(topicName)
            .partitions(partitions)
            .replicas(replicas)
            .build();
    }

    @Bean
    public KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate(ProducerFactory<Long, LinkUpdateRequest> producerFactory) {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<Long, LinkUpdateRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

}
