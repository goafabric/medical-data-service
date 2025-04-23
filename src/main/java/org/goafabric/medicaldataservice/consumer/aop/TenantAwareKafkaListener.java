package org.goafabric.medicaldataservice.consumer.aop;

import org.springframework.kafka.annotation.KafkaListener;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@KafkaListener // This makes it a Kafka listener
public @interface TenantAwareKafkaListener {
    String[] topics();
    String groupId() default "";
    String containerFactory() default "";

}
