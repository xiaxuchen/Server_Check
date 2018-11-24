package com.cxyz.check.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializer extends JsonSerializer<Date>
{
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)throws IOException, JsonProcessingException
    {
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = format.format(date);
        gen.writeString(formattedDate);
    }
}