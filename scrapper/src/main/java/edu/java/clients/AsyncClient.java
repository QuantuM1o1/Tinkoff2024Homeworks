package edu.java.clients;

import reactor.core.publisher.Mono;

public interface AsyncClient<T extends Record, U extends Record> {
    Mono<? extends T> fetch(U args);
}
