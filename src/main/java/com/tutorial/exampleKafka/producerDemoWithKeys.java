package com.tutorial.exampleKafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class producerDemoWithKeys {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final Logger logger= LoggerFactory.getLogger(producerDemoWithKeys.class);
//        Create Producer Properties
        String bootstrapServers="127.0.0.1:9092";
        Properties properties= new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

//        create producer
        KafkaProducer<String,String> producer=new KafkaProducer<String, String>(properties);

        for(int i=0;i<10;i++) {
            //create a producer record
            String topic ="first_topic";
            String value ="Hello World !! producerWithKeys "+ Integer.toString(i);
            String key ="id_" +Integer.toString(i);
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, value);

            logger.info("Key: "+key);
            //        produce messages takes a producer record.
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        //record successfully sent
                        logger.info("Received Metadata successfully: \n" +
                                "Topic:" + recordMetadata.topic() + "\n" +
                                "partition:" + recordMetadata.partition() + "\n" +
                                "offset:" + recordMetadata.timestamp() + "\n" +
                                "Timestamp" + recordMetadata.timestamp());
                    } else {
                        logger.error("Error while producing", e);
                    }
                }

            }).get();//block the send to make it synchronous. Dont do this production

        }
// you need flush the producer in order to send the messages
        producer.flush();
    }
}
