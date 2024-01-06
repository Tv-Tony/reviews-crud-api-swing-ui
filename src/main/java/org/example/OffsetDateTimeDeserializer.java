package org.example;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeDeserializer implements JsonDeserializer<OffsetDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public OffsetDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateString = json.getAsJsonPrimitive().getAsString();

        try {
            // Try to parse with the expected format
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
            // Assume UTC as the default time zone
            return localDateTime.atOffset(ZoneOffset.UTC);
        } catch (java.time.format.DateTimeParseException e) {
            // Handle the case when the offset is missing
            throw new JsonParseException("Error parsing date: " + dateString, e);
        }
    }
}