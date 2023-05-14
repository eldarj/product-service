package com.ejahijagic.productservice.client;

import com.ejahijagic.productservice.exceptions.ProductException;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface ProductClient {

    @Async
    CompletableFuture<String> get(String productId) throws ProductException;
}
