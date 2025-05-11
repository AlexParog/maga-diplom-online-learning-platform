package ru.parog.magacourseservice.config;

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
    public Counter coursesViewedCounter() {
        return Counter.builder("app.courses.viewed")
                .description("Количество просмотров курсов")
                .register(meterRegistry);
    }

    @Bean
    public Counter coursesCreatedCounter() {
        return Counter.builder("app.courses.created")
                .description("Количество созданных курсов")
                .register(meterRegistry);
    }

    @Bean
    public Counter modulesCreatedCounter() {
        return Counter.builder("app.modules.created")
                .description("Количество созданных модулей")
                .register(meterRegistry);
    }

    @Bean
    public Counter lessonsViewedCounter() {
        return Counter.builder("app.lessons.viewed")
                .description("Количество просмотров уроков")
                .register(meterRegistry);
    }

    @Bean
    public Counter enrollmentsCounter() {
        return Counter.builder("app.enrollments")
                .description("Количество зачислений на курсы")
                .register(meterRegistry);
    }

    @Bean
    public Timer courseFetchTimer() {
        return Timer.builder("app.courses.fetch.time")
                .description("Время получения курсов")
                .register(meterRegistry);
    }
}
