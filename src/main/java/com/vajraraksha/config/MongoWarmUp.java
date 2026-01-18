package com.vajraraksha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoWarmUp {

    @Autowired
    private MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void warmUp() {
        mongoTemplate.getDb().getName();
    }
}
