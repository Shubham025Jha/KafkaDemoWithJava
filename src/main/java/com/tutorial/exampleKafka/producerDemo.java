package com.tutorial.exampleKafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class producerDemo {
    public static void main(String[] args) {
//        System.out.println("Hello World! I am Kafka.");
//        Create Producer Properties
        String bootstrapServers="127.0.0.1:9092";
        Properties properties= new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

//        create producer
        KafkaProducer<String,String> producer=new KafkaProducer<String, String>(properties);
//create a producer record
        ProducerRecord<String,String> record=
                new ProducerRecord<String,String>("first_topic","Hello World! Kafka this side.");
//        produce messages takes a producer record. send is asynchronous so you need to flush it in order to see it.
        producer.send(record);
// you need flush the producer in order to send the messages
        producer.flush();
    }
}
