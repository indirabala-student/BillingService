package com.meatsfresh.BillingService.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix="services")
public class ServiceUrlProperties {

    private String vendorBaseUrl;

    private String orderBaseUrl;

}
