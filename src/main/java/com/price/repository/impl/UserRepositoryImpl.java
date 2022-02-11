package com.price.repository.impl;

import com.price.dto.Subscriber;
import com.price.repository.UserRepository;
import com.price.types.CCYPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UserRepositoryImpl implements UserRepository {

    private Map<String, List<Subscriber>> subscribers = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        for (CCYPair ccyPair : CCYPair.values()) {
            subscribers.put(ccyPair.name(), new ArrayList<>(200));
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {

        for (String ccyPair : subscriber.getCcyPair().keySet()) {
            List<Subscriber> subscribersList = subscribers.get(ccyPair);

            if (Objects.isNull(subscribersList)) {
                subscribersList = new ArrayList<>(200);
            }

            for (Subscriber s : subscribersList) {
                if (s.getName().equals(subscriber.getName()) && s.getSurname().equals(subscriber.getSurname())) {
                    log.error("Subscriber {} already exists", subscriber);
                    throw new RuntimeException("Subscriber " + s.getName() + " exists for pair " + ccyPair);
                }
            }

            subscribersList.add(subscriber);
            subscribers.put(ccyPair, subscribersList);
            log.info("Subscriber {} added successfully to pair {}", subscriber.getName(), ccyPair);
        }
    }

    @Override
    public List<Subscriber> getSubscrbersByPair(String pair) {
        return subscribers.get(pair);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        log.info("Removing subscriber: {}", subscriber);
        for (String ccyPair : subscriber.getCcyPair().keySet()) {
            List<Subscriber> subscribersList = subscribers.get(ccyPair);
            if (Objects.nonNull(subscribersList)) {
                Optional<Subscriber> first = subscribersList
                        .stream()
                        .filter(s -> s.getName().equals(subscriber.getName()) &&
                                s.getSurname().equals(subscriber.getSurname()) &&
                                Objects.nonNull(subscriber.getUuid()))
                        .findFirst();
                first.ifPresent(subscribersList::remove);
                log.info("User {} successfully removed", subscriber);
            }
        }
    }
}
