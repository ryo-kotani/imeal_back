package com.imeal.imeal_back.common.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix="cors")
@Getter
@Setter
public class CorsProperties {
  private List<String> allowedOrigins;
}
