//package org.goafabric.medicaldataservice.consumer;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class CustomKafkaConsumerConfig {
//    @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers;
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> latestKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(latestConsumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> latestConsumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(latestConsumerConfigs());
//    }
//
//    @Bean
//    public Map<String, Object> latestConsumerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // todo
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//        return props;
//    }
//
//}
