
package com.kafka.producer.kafkaproducer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class App {
    public static void main(String[] args) {
        runConsumer();
    }

    static void runConsumer() {
        Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

        int noMessageFound = 0;

        while (true) {
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                {
                    break;
                } else {
                    continue;
                }
            }
            consumerRecords.forEach(record -> {
                System.out.println(record.value());
            });

            consumer.commitAsync();
        }
        consumer.close();
    }
}
