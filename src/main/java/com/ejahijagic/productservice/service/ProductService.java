package com.ejahijagic.productservice.service;

import com.ejahijagic.productservice.client.ProductClient;
import com.ejahijagic.productservice.exceptions.ProductException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

        try {
            result.put("product", parse(product.join()));
            result.put("review", parse(review.join()));
        } catch (ParseException e) {
            throw new ProductException("Error parsing product response as JSON");
        }


        return result;
    }

    private JSONObject parse(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(json);
    }
}
