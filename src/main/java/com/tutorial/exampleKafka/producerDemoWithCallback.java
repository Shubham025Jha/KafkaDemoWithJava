package com.tutorial.exampleKafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class producerDemoWithCallback {
    public static void main(String[] args) {
        final Logger logger= LoggerFactory.getLogger(producerDemoWithCallback.class);
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
                new ProducerRecord<String,String>("first_topic","Hello World !! Kafka this side from producerWithCallback.");
//        produce messages takes a producer record. send is asynchronous so you need to flush it in order to see it.
        producer.send(record,new Callback(){
            public void onCompletion(RecordMetadata recordMetadata, Exception e){
                if(e==null){
                    //record successfully sent
                    logger.info("Received Metadata successfully: \n"+
                            "Topic:"+recordMetadata.topic()+"\n"+
                            "partition:"+ recordMetadata.partition()+"\n"+
                            "offset:"+ recordMetadata.timestamp()+"\n"+
                            "Timestamp"+ recordMetadata.timestamp());
                }
                else{
                    logger.error("Error while producing", e);
                }
            }
        });
// you need flush the producer in order to send the messages
        producer.flush();
    }
}
