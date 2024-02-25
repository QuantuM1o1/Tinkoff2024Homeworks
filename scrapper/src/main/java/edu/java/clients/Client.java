package edu.java.clients;

import reactor.core.publisher.Mono;

public interface Client<T extends Record> {
    Mono<? extends T> fetch(String[] args);
}
