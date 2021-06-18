package org.core.util.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.sql.Timestamp;

public class JsonDateTimeSerializer extends JsonSerializer<Timestamp> {
    @Override
    public void serialize(final Timestamp timestamp, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(timestamp.toString());
    }
}
