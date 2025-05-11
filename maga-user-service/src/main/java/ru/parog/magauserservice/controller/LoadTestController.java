package ru.parog.magauserservice.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/api/user-service/load-test")
@RequiredArgsConstructor
public class LoadTestController {

    private final MeterRegistry meterRegistry;
    private final Random random = new Random();
    private final AtomicInteger concurrentRequests = new AtomicInteger(0);

    @GetMapping("/cpu")
    @Timed("load.test.cpu")
    public ResponseEntity<Map<String, Object>> generateCpuLoad(
            @RequestParam(defaultValue = "500") int iterations,
            @RequestParam(defaultValue = "500") int complexity) {

        Timer timer = Timer.builder("load.test.cpu.execution")
                .description("Время выполнения CPU-нагрузки")
                .register(meterRegistry);

        return timer.record(() -> {
            log.info("Генерация CPU нагрузки: итераций={}, сложность={}", iterations, complexity);

            double result = 0;
            for (int i = 0; i < iterations; i++) {
                for (int j = 0; j < complexity; j++) {
                    result += Math.sin(random.nextDouble()) * Math.cos(random.nextDouble());
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("iterations", iterations);
            response.put("complexity", complexity);
            response.put("result", result);
            return ResponseEntity.ok(response);
        });
    }

    @GetMapping("/memory")
    @Timed("load.test.memory")
    public ResponseEntity<Map<String, Object>> generateMemoryLoad(
            @RequestParam(defaultValue = "1000") int elements) {

        log.info("Генерация нагрузки на память: элементов={}", elements);

        Map<String, Object> data = new HashMap<>();

        for (int i = 0; i < elements; i++) {
            byte[] bytes = new byte[1024]; // 1KB на каждый элемент
            random.nextBytes(bytes);
            data.put(UUID.randomUUID().toString(), bytes);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("elements", elements);
        response.put("memorySize", elements * 1024);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/latency")
    @Timed("load.test.latency")
    public ResponseEntity<Map<String, Object>> generateLatency(
            @RequestParam(defaultValue = "500") int millis) throws InterruptedException {

        log.info("Генерация задержки: {}мс", millis);

        TimeUnit.MILLISECONDS.sleep(millis);

        Map<String, Object> response = new HashMap<>();
        response.put("latency", millis);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/error-on-load")
    @Timed("load.test.error")
    public ResponseEntity<Object> errorOnHighLoad(
            @RequestParam(defaultValue = "10") int threshold,
            @RequestParam(defaultValue = "500") int processingTimeMs) {

        int currentRequests = concurrentRequests.incrementAndGet();
        try {
            log.info("Запрос #{}. Пороговое значение: {}", currentRequests, threshold);

            // Имитация обработки запроса
            if (processingTimeMs > 0) {
                TimeUnit.MILLISECONDS.sleep(processingTimeMs);
            }

            // Возвращаем ошибку, если превышен порог одновременных запросов
            if (currentRequests > threshold) {
                log.error("Превышен порог одновременных запросов: {} > {}", currentRequests, threshold);
                return ResponseEntity.internalServerError()
                        .body(Map.of(
                                "error", "Сервис перегружен",
                                "currentRequests", currentRequests,
                                "threshold", threshold
                        ));
            }

            // Успешный ответ
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "currentRequests", currentRequests,
                    "threshold", threshold
            ));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().body(Map.of("error", "Обработка прервана"));
        } finally {
            concurrentRequests.decrementAndGet();
        }
    }
}
