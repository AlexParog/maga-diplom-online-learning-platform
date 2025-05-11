package ru.parog.magatestservice.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    private final MeterRegistry meterRegistry;

    public MetricsConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Bean
    public Counter testsViewedCounter() {
        return Counter.builder("app.tests.viewed")
                .description("Количество просмотров тестов")
                .register(meterRegistry);
    }

    @Bean
    public Counter testsCreatedCounter() {
        return Counter.builder("app.tests.created")
                .description("Количество созданных тестов")
                .register(meterRegistry);
    }

    @Bean
    public Counter questionsCreatedCounter() {
        return Counter.builder("app.questions.created")
                .description("Количество созданных вопросов")
                .register(meterRegistry);
    }

    @Bean
    public Counter optionsCreatedCounter() {
        return Counter.builder("app.options.created")
                .description("Количество созданных вариантов ответов")
                .register(meterRegistry);
    }

    @Bean
    public Counter questionsViewedCounter() {
        return Counter.builder("app.questions.viewed")
                .description("Количество просмотров вопросов")
                .register(meterRegistry);
    }

    @Bean
    public Timer testFetchTimer() {
        return Timer.builder("app.tests.fetch.time")
                .description("Время получения тестов")
                .register(meterRegistry);
    }

    @Bean
    public Timer questionFetchTimer() {
        return Timer.builder("app.questions.fetch.time")
                .description("Время получения вопросов")
                .register(meterRegistry);
    }
}
