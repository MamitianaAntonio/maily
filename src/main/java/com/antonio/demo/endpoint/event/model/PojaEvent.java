package com.antonio.demo.endpoint.event.model;

import static com.antonio.demo.endpoint.event.EventStack.EVENT_STACK_1;

import com.antonio.demo.PojaGenerated;
import com.antonio.demo.endpoint.event.EventStack;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;

@PojaGenerated
public abstract class PojaEvent implements Serializable {
  @Getter
  @Setter
  protected int attemptNb;

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
    if (getEventStack().equals(EventStack.EVENT_STACK_1))
      return "com.antonio.demo.event1";
    return "com.antonio.demo.event2";
  }
}
