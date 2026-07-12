package com.antonio.demo.endpoint.rest.controller;

import com.antonio.demo.endpoint.event.EventProducer;
import com.antonio.demo.endpoint.event.model.SendEmailRequested;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HelloWorldController {
  private final EventProducer<SendEmailRequested> eventProducer;

  @GetMapping("/hello")
  @SneakyThrows
  public String helloWorld(
      @RequestParam String to, @RequestParam String subject, @RequestParam String body) {
    var event = SendEmailRequested.builder().to(to).subject(subject).htmlBody(body).build();
    eventProducer.accept(List.of(event));
    return "Email sent to " + to;
  }
}
