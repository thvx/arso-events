package com.um.eventos.domain.events;

import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public interface DomainEvent {
    String getEventType();
    String getAggregateId();
    default String toJson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create()
                .toJson(this);
    }

    default String generateEventId() {
        return UUID.randomUUID().toString();
    }

    default String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}