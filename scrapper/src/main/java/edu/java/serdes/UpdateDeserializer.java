package edu.java.serdes;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import scrapper.Updates;

public class UpdateDeserializer implements Deserializer<Updates.Update> {
    @Override
    public Updates.Update deserialize(String topic, byte[] data) {
        try {
            return Updates.Update.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            throw new SerializationException("Error when deserializing byte[] to protobuf", e);
        }
    }
}
