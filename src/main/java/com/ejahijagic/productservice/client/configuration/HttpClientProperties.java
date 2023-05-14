package com.ejahijagic.productservice.client.configuration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "product.services")
public class HttpClientProperties {

    private String externalProductUrl;

    private String reviewServiceUrl;

    private String reviewServiceToken;
}
