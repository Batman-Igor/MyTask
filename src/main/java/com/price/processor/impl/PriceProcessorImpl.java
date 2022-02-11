package com.price.processor.impl;

import com.price.configuration.PairUpdater;
import com.price.dto.Subscriber;
import com.price.processor.PriceProcessor;
import com.price.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class PriceProcessorImpl implements PriceProcessor {

  private final UserService userService;
  private final PairUpdater pairUpdater;

  public PriceProcessorImpl(UserService userService, PairUpdater pairUpdater) {
    this.userService = userService;
    this.pairUpdater = pairUpdater;
  }

  @Override
  public void onPrice(String ccyPair, double rate) {
    pairUpdater.updateTimer(ccyPair, rate);
  }

  @Override
  public void subscribe(Subscriber subscriber) {
    userService.addSubscriber(subscriber);
  }

  @Override
  public void unsubscribe(Subscriber subscriber) {
    userService.removeSubscriber(subscriber);
  }
}
