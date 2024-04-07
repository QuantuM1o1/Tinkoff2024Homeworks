package edu.java.bot.serdes;

import bot.Updates;
import org.apache.kafka.common.serialization.Serializer;

public class UpdateSerializer implements Serializer<Updates.Update> {
    @Override
    public byte[] serialize(String topic, Updates.Update data) {
        return data.toByteArray();
    }
}
