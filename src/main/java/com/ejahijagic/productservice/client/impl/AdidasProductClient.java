package com.ejahijagic.productservice.client.impl;

import com.ejahijagic.productservice.exceptions.ProductException;
import com.ejahijagic.productservice.client.ProductClient;
import com.ejahijagic.productservice.client.configuration.HttpClientProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdidasProductClient implements ProductClient {

    private final HttpClientProperties httpClientProperties;

    @Override
    public CompletableFuture<String> get(String productId) throws ProductException {
        var url = httpClientProperties.getExternalProductUrl() + "/" + productId;

        log.info("Fetching adidas product by id: {} from {}", productId, url);

        WebDriverManager.chromedriver().setup();
        var options = new ChromeOptions();
        var driver = new ChromeDriver(options);

        var json = "";

        try {
            driver.get("view-source:https://www.adidas.co.uk/api/products/BB5476");
            var response = driver.findElement(By.tagName("body")).getText();
            json = response.replace("Line wrap\n", "");
        } catch (Exception e) {
            log.error("Error fetching product by id: {} from {}", productId, url, e);
            throw new ProductException("Error fetching product by id: " + productId + " from " + url);
        }

        driver.quit();
        return CompletableFuture.completedFuture(json);
    }
}
