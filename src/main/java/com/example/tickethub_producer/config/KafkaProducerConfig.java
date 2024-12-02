package com.example.tickethub_producer.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean(name = "producerProperties")
    public Properties producerProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "10000");
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "12000");
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "prod-1");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        return props;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KafkaProducer<String, String> kafkaProducer(@Qualifier("producerProperties") Properties producerProperties) {
        return new KafkaProducer<>(producerProperties);
    }
}
