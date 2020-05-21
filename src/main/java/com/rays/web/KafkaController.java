package com.rays.web;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Ray Ma on 2020/4/29.
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {


    private static final String TOPIC = "test";
    private static final String BROKER_LIST = "myaliyun:9092";
    private static KafkaConsumer<String,String> consumer = null;


    static {
        Properties configs = initConfig();
        consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Arrays.asList(TOPIC));
    }

    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers",BROKER_LIST);
        properties.put("group.id","0");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.offset.reset", "earliest");
        return properties;
    }



    public static void main(String[] args) {

        while(true){
            ConsumerRecords<String,String> records =consumer.poll(5000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
            }
        }
    }


    public static void listen(){

    }
}
