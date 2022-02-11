package com.price.service.impl;

import com.price.dto.Subscriber;
import com.price.repository.UserRepository;
import com.price.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceTrottler implements UserService {

  private final UserRepository subscribersRepository;

  public PriceTrottler(UserRepository userRepository) {
    this.subscribersRepository = userRepository;
  }

  @Override
  public void addSubscriber(Subscriber subscriber) {
    subscribersRepository.addSubscriber(subscriber);
  }

  @Override
  public Optional<List<Subscriber>> getSubscribersByPair(String pair) {
    return Optional.ofNullable(subscribersRepository.getSubscrbersByPair(pair));
  }

  @Override
  public void removeSubscriber(Subscriber subscriber) {
    subscribersRepository.removeSubscriber(subscriber);
  }
}
