package com.example.producers;

import com.example.model.Coffee;
import reactor.core.publisher.Flux;

public interface StreamPublisher {
    Flux<Coffee> getStreamPublisher();
}
