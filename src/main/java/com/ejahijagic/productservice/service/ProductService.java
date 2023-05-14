package com.ejahijagic.productservice.service;

import com.ejahijagic.productservice.client.ProductClient;
import com.ejahijagic.productservice.exceptions.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductClient productReviewClient;

    private final ProductClient adidasProductClient;

    public Map<String, Object> getProductById(String productId) throws ProductException {
        var result = new HashMap<String, Object>();

        CompletableFuture<String> product = adidasProductClient.get(productId);
        CompletableFuture<String> review = productReviewClient.get(productId);

        CompletableFuture.allOf(product, review).join();

        result.put("product", product.join());
        result.put("review", review.join());

        return result;
    }
}
