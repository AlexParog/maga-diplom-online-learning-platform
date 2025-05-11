package ru.parog.magatestservice.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.observation.ClientRequestObservationConvention;
import org.springframework.http.client.observation.DefaultClientRequestObservationConvention;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final MeterRegistry meterRegistry;
    private final ObservationRegistry observationRegistry;

    public RestTemplateConfig(MeterRegistry meterRegistry, ObservationRegistry observationRegistry) {
        this.meterRegistry = meterRegistry;
        this.observationRegistry = observationRegistry;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(java.time.Duration.ofSeconds(5))
                .setReadTimeout(java.time.Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public ClientRequestObservationConvention observationConvention() {
        return new DefaultClientRequestObservationConvention("http.client.requests");
    }

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .build();
    }
}
