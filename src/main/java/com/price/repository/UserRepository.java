package com.price.repository;

import com.price.dto.Subscriber;

import java.util.List;

public interface UserRepository {
    void addSubscriber(Subscriber subscriber);

    List<Subscriber> getSubscrbersByPair(String pair);

    void removeSubscriber(Subscriber subscriber);
}
