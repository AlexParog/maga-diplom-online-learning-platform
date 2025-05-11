package ru.parog.magauserservice.config;

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
    public Counter loginAttemptsCounter() {
        return Counter.builder("app.auth.login.attempts")
                .description("Количество попыток входа")
                .register(meterRegistry);
    }

    @Bean
    public Counter loginSuccessCounter() {
        return Counter.builder("app.auth.login.success")
                .description("Количество успешных входов")
                .register(meterRegistry);
    }

    @Bean
    public Counter loginFailedCounter() {
        return Counter.builder("app.auth.login.failed")
                .description("Количество неудачных входов")
                .register(meterRegistry);
    }

    @Bean
    public Timer userOperationsTimer() {
        return Timer.builder("app.user.operations")
                .description("Время выполнения операций с пользователями")
                .register(meterRegistry);
    }
}
