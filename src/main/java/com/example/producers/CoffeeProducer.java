package com.example.producers;

import com.example.model.Coffee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Getter
@AllArgsConstructor
public class CoffeeProducer {
    private final Flux<Coffee> coffeeFlux = this.produce();
    private Flux<Coffee> produce() {
        return Flux.generate(() -> {
            return 0;
        }, (state, sink) -> {
            sink.next(new Coffee(state.toString(), "state" + state));
            if (state == 100) {
                sink.complete();
            }

            return state + 1;
        });
    }
}
