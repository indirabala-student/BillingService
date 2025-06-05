package com.meatsfresh.BillingService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder){
        return builder.build();
    }

    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
