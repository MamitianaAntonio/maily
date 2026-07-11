package com.antonio.demo.endpoint.event.consumer.model;

import com.antonio.demo.PojaGenerated;
import com.antonio.demo.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
