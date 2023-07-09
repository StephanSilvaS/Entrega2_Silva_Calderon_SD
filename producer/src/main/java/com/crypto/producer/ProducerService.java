package com.crypto.producer;

import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.types.AblyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;


    public void ablyAPI() throws AblyException{
        // Configurar la conexión
        String apiKey = "KMNnHQ.DbzYqQ:Ur6_rOy3cYjKvlazKcIiHNCSW_QkhjtmtCm4sFZ-J98";
        AblyRealtime ably = new AblyRealtime(apiKey);

        // Obtener un canal
        String channelName = "[product:ably-coindesk/crypto-pricing]btc:usd";
        io.ably.lib.realtime.Channel channel = ably.channels.get(channelName);

        // Suscribirse a los mensajes
        channel.subscribe(message -> {
            String m = message.data.toString();
            
            List<String> listaValores = new ArrayList<String>();
            listaValores.add(m);
            
            if(listaValores.size() == 10){
                ably.close();
            }
            
            System.out.println(m);
            kafkaTemplate.send(topic, m);
        });
    }
}
