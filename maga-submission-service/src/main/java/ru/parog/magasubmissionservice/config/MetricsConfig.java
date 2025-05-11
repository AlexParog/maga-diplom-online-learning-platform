package ru.parog.magasubmissionservice.config;

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
    public Counter submissionsStartedCounter() {
        return Counter.builder("app.submissions.started")
                .description("Количество начатых прохождений тестов")
                .register(meterRegistry);
    }

    @Bean
    public Counter submissionsCompletedCounter() {
        return Counter.builder("app.submissions.completed")
                .description("Количество завершенных прохождений тестов")
                .register(meterRegistry);
    }

    @Bean
    public Counter submissionsAnswersCounter() {
        return Counter.builder("app.submissions.answers")
                .description("Количество отправленных ответов")
                .register(meterRegistry);
    }

    @Bean
    public Timer submissionCompletionTimer() {
        return Timer.builder("app.submissions.completion.time")
                .description("Время на прохождение теста от начала до завершения")
                .register(meterRegistry);
    }

    @Bean
    public Timer evaluationTimer() {
        return Timer.builder("app.submissions.evaluation.time")
                .description("Время на проверку ответов")
                .register(meterRegistry);
    }
}
