package com.cxyz.check.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomDateListSerializer extends JsonSerializer<List<Date>>
{
    public void serialize(List<Date> dates, JsonGenerator gen, SerializerProvider provider)throws IOException, JsonProcessingException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gen.writeStartArray();
        for (Date date : dates)
        {
            gen.writeString(format.format(date));
        }
        gen.writeEndArray();
    }
}