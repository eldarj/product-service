package com.ejahijagic.productservice.client.impl;

import com.ejahijagic.productservice.client.ProductClient;
import com.ejahijagic.productservice.client.configuration.HttpClientProperties;
import com.ejahijagic.productservice.exceptions.ProductException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductReviewClient implements ProductClient {

    private static final String PRODUCT_SERVICE_TOKEN_HEADER = "X-Service-Token";

    private final RestTemplate restTemplate;

    private final HttpClientProperties httpClientProperties;

    @Override
    public CompletableFuture<String> get(String productId) throws ProductException {
        var url = httpClientProperties.getReviewServiceUrl() + "/" + productId;

        log.info("Fetching product review by id: {} from {}", productId, url);

        var headers = new HttpHeaders();
        headers.set(PRODUCT_SERVICE_TOKEN_HEADER, httpClientProperties.getReviewServiceToken());

        try {
            var response = restTemplate.exchange(RequestEntity.get(url).headers(headers).build(), String.class);
            return CompletableFuture.completedFuture(response.getBody());
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                return CompletableFuture.completedFuture(null);
            }

            throw new ProductException("Error fetching product review by id: " + productId + " from " + url);
        } catch (Exception e) {
            throw new ProductException("Error fetching product review by id: " + productId + " from " + url);
        }
    }
}
