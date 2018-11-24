package com.cxyz.check.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CustomTimeStampSerializer extends JsonSerializer<Timestamp> {


    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = format.format(timestamp);
        jsonGenerator.writeString(formattedDate);
    }
}
