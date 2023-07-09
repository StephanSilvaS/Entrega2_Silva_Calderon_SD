package com.crypto.consumer;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerService{

    @KafkaListener(topics = "${kafka.topic}")
    public void receive(String valueCrypto){

        // Ver que valor tira esta cosa
        System.out.println(valueCrypto);

        String cryptoValue = jsonToValue(valueCrypto);
    }

    private static String jsonToValue(String valueCrypto){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String value = null;
        try{
            value = objectMapper.readValue(valueCrypto, String.class);
        } catch (IOException e){
            
        }
        return value;
    }
}