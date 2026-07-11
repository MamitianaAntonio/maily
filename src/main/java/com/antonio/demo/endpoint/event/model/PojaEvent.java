package com.antonio.demo.endpoint.event.model;

import com.antonio.demo.PojaGenerated;
import java.io.Serializable;
import java.time.Duration;
import com.antonio.demo.endpoint.event.EventStack;

@PojaGenerated
public abstract class PojaEvent implements Serializable {
  public abstract Duration maxConsumerDuration();

  public Duration eventHandlerInitMaxDuration() {
    return Duration.ofSeconds(90); // note(init-visibility)
  }

  private Duration randomConsumerBackoffBetweenRetries() {
    return Duration.ofSeconds(
        (int) (Math.random() * maxConsumerBackoffBetweenRetries().toSeconds()));
  }

  public abstract Duration maxConsumerBackoffBetweenRetries();

  public final Duration randomVisibilityTimeout() {
    return Duration.ofSeconds(
        eventHandlerInitMaxDuration().toSeconds()
            + maxConsumerDuration().toSeconds()
            + randomConsumerBackoffBetweenRetries().toSeconds());
  }

  public EventStack getEventStack() {
    return EVENT_STACK_1;
  }

  public String getEventSource() {
    if (getEventStack().equals(EVENT_STACK_1)) return "com.antonio.demo.event1";
    return "com.antonio.demo.event2";
  }
}
