package com.price.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.price.dto.Subscriber;
import com.price.service.UserService;
import com.price.types.CCYPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Configuration
public class PairUpdater {

    private final Environment env;
    private final UserService userService;
    private final Map<String, Timer> timers = new HashMap<>();
    private Map<String, Integer> rate;

    public PairUpdater(UserService userService, Environment env) {
        this.userService = userService;
        this.env = env;
    }

    @PostConstruct
    private void init() {
        try {
            rate = new ObjectMapper().readValue(env.getProperty("rate"), new TypeReference<>() {
            });
            createTimers();
        } catch (JsonProcessingException e) {
            log.error("Error: {}", e.getMessage());
        }
    }

    private void createTimers() {
        for (CCYPair pairName : CCYPair.values()) {
            TimerTask timerTask = createTimerTask(pairName.name());
            Timer timer = new Timer();
            timers.put(pairName.name().toLowerCase(), timer);
            timer.scheduleAtFixedRate(timerTask, 0, rate.get(pairName.name().toLowerCase()));
        }
    }

    private TimerTask createTimerTask(String pairName) {

        String name = Thread.currentThread().getName();
        return new TimerTask() {
            @Override
            public void run() {
                // Generating new price (for simulation)
                int nextPrice = new Random().nextInt(100);
                log.info("New price is for pair {} : {}", pairName, nextPrice);

                // Updating price for all subscribers
                List<Subscriber> subscribers = userService.getSubscribersByPair(pairName)
                        .orElseGet(ArrayList::new);
                for (Subscriber s : subscribers) {
                    s.getCcyPair().put(name, nextPrice);
                    log.info("CcyPair updated for user {}", s.getName());
                }
            }
        };
    }

    public void updateTimer(String ccyPair, Double rate) {
        long roundedRate = Math.round(rate * 1000);
        log.info("Updating rate of {} to {} miliseconds", ccyPair, roundedRate);
        if (stopTimer(ccyPair)) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(createTimerTask(ccyPair), 0, roundedRate);
            timers.put(ccyPair.toLowerCase(), timer);
        }
    }

    public boolean stopTimer(String ccyPair) {
        Timer currentTimer = null;
        for (Map.Entry<String, Timer> t : timers.entrySet()) {
            if (t.getKey().equals(ccyPair.toLowerCase())) {
                currentTimer = t.getValue();
            }
        }

        if (Objects.nonNull(currentTimer)) {
            currentTimer.cancel();
            timers.remove(ccyPair.toLowerCase());
            log.info("Timer {} stoped", ccyPair);
            return true;
        }
        log.info("Timer {} not founed", ccyPair);
        return false;
    }
}
