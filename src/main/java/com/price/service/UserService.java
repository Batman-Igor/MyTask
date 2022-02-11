package com.price.service;

import com.price.dto.Subscriber;

import java.util.List;
import java.util.Optional;

public interface UserService {
  void addSubscriber(Subscriber subscriber);

  Optional<List<Subscriber>> getSubscribersByPair(String pair);

  void removeSubscriber(Subscriber subscriber);
}
