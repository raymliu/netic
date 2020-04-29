//package com.rays.filter;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.JsonToken;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import org.apache.commons.lang3.StringUtils;
//import org.joda.time.DateTime;
//import org.joda.time.format.ISODateTimeFormat;
//
//import java.io.IOException;
//
///**
// * Created by hand on 2017/4/25.
// */
//public class CustomDateTimeDeserializer extends JsonDeserializer<DateTime> {
//
//
//    @Override
//    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//        JsonToken jt = jsonParser.getCurrentToken();
//        if(jt == JsonToken.VALUE_STRING){
//            String trim = jsonParser.getText().trim();
//            if(StringUtils.isEmpty(trim)){
//                return null;
//            }
//            return ISODateTimeFormat.dateTimeParser().parseDateTime(trim);
//        }
//        if(jt == JsonToken.VALUE_NUMBER_INT){
//            return new DateTime(jsonParser.getLongValue());
//        }
//        throw deserializationContext.mappingException(handledType());
//    }
//}
