package com.crypto.consumer;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class ConsumerConnector {
    private Cluster cluster;
    private Session session;

    public void connect(String node, int port) {
        cluster = Cluster.builder()
                .addContactPoint(node)
                .withPort(port)
                .build();
        session = cluster.connect();
    }

    public void close() {
        session.close();
        cluster.close();
    }

    public void insertData(String key, String value) {
        session.execute("INSERT INTO your_table (key, value) VALUES (?, ?)", key, value);
    }
}
