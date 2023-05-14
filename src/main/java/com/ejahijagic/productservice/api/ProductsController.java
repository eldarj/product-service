package com.ejahijagic.productservice.api;

import com.ejahijagic.productservice.exceptions.ProductException;
import com.ejahijagic.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public Map<String, Object> findById(@PathVariable String productId) throws ProductException {
        return productService.getProductById(productId);
    }
}
