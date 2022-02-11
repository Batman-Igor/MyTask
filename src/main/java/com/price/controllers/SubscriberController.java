package com.price.controllers;

import com.price.dto.Subscriber;
import com.price.processor.PriceProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

    private final PriceProcessor processor;

    public SubscriberController(PriceProcessor processor) {
        this.processor = processor;
    }

    @PostMapping(value = "/subscribe", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> subscribeService(@RequestBody Subscriber subscriber) {
        log.info("Adding user {}", subscriber.getName());
        try {
            processor.subscribe(subscriber);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error adding user: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Done");
    }

    @DeleteMapping(value = "/unsubscribe", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> unsubscribeService(@RequestBody Subscriber subscriber) {
        log.info("Removing user {}", subscriber.getName());
        try {
            processor.unsubscribe(subscriber);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Done");
    }


    @PostMapping(value = "/onpost", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> onPost(@RequestParam String ccyPair, @RequestParam Double rate) {
        log.info("Changing ccyPair of {} to rate {}", ccyPair, rate);
        try {
            processor.onPrice(ccyPair, rate);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Done");
    }
}
