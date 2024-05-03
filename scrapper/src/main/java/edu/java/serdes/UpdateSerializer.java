package edu.java.serdes;

import org.apache.kafka.common.serialization.Serializer;
import scrapper.Updates;

public class UpdateSerializer implements Serializer<scrapper.Updates.Update> {
    @Override
    public byte[] serialize(String topic, Updates.Update data) {
        return data.toByteArray();
    }
}
