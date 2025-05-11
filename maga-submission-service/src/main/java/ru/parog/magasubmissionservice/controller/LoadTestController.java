package ru.parog.magasubmissionservice.controller;

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

@Slf4j
@RestController
@RequestMapping("/api/submission-service/load-test")
@RequiredArgsConstructor
public class LoadTestController {

    private final MeterRegistry meterRegistry;
    private final Random random = new Random();

    @GetMapping("/unified")
    @Timed("load.test.unified")
    public ResponseEntity<Map<String, Object>> unifiedLoadTest(
            @RequestParam(defaultValue = "200") int cpuIterations,
            @RequestParam(defaultValue = "200") int cpuComplexity,
            @RequestParam(defaultValue = "500") int memoryElements,
            @RequestParam(defaultValue = "100") int latencyMillis,
            @RequestParam(defaultValue = "50") int simulatedSubmissions,
            @RequestParam(defaultValue = "5") int questionsPerSubmission) throws InterruptedException {

        long startTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();

        // 1. CPU нагрузка
        Timer cpuTimer = Timer.builder("load.test.unified.cpu")
                .description("Время выполнения CPU-нагрузки в unified методе")
                .register(meterRegistry);

        double cpuResult = cpuTimer.record(() -> {
            log.info("Unified test: генерация CPU нагрузки: итераций={}, сложность={}",
                    cpuIterations, cpuComplexity);

            double result = 0;
            for (int i = 0; i < cpuIterations; i++) {
                for (int j = 0; j < cpuComplexity; j++) {
                    result += Math.sin(random.nextDouble()) * Math.cos(random.nextDouble());
                }
            }

            return result;
        });

        // 2. Нагрузка на память
        log.info("Unified test: генерация нагрузки на память: элементов={}", memoryElements);

        Map<String, Object> memoryData = new HashMap<>();

        for (int i = 0; i < memoryElements; i++) {
            byte[] bytes = new byte[1024]; // 1KB на каждый элемент
            random.nextBytes(bytes);
            memoryData.put(UUID.randomUUID().toString(), bytes);
        }

        // 3. Имитация задержки
        log.info("Unified test: генерация задержки: {}мс", latencyMillis);
        TimeUnit.MILLISECONDS.sleep(latencyMillis);

        // 4. Симуляция прохождения тестов
        log.info("Unified test: симуляция прохождения тестов: прохождений={}, вопросов={}",
                simulatedSubmissions, questionsPerSubmission);

        int correctAnswers = 0;

        for (int i = 0; i < simulatedSubmissions; i++) {
            for (int j = 0; j < questionsPerSubmission; j++) {
                // Имитация процесса проверки ответов
                correctAnswers += random.nextInt(2); // 0 или 1
            }
        }

        double avgScore = (double) correctAnswers / (simulatedSubmissions * questionsPerSubmission);

        // 5. Системная информация
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        int availableProcessors = runtime.availableProcessors();

        // Счетчик вызовов
        meterRegistry.counter("load.test.unified.calls").increment();

        // Подготовка итогового ответа
        long endTime = System.currentTimeMillis();

        // CPU результаты
        Map<String, Object> cpuResults = new HashMap<>();
        cpuResults.put("iterations", cpuIterations);
        cpuResults.put("complexity", cpuComplexity);
        cpuResults.put("result", cpuResult);
        response.put("cpu", cpuResults);

        // Результаты по памяти
        Map<String, Object> memoryResults = new HashMap<>();
        memoryResults.put("elements", memoryElements);
        memoryResults.put("allocatedSize", memoryElements * 1024);
        memoryResults.put("jvmMax", maxMemory);
        memoryResults.put("jvmTotal", totalMemory);
        memoryResults.put("jvmFree", freeMemory);
        memoryResults.put("jvmUsed", usedMemory);
        memoryResults.put("usagePercent", (double) usedMemory / maxMemory * 100);
        response.put("memory", memoryResults);

        // Результаты задержки
        Map<String, Object> latencyResults = new HashMap<>();
        latencyResults.put("requestedLatency", latencyMillis);
        response.put("latency", latencyResults);

        // Результаты симуляции
        Map<String, Object> simulationResults = new HashMap<>();
        simulationResults.put("submissions", simulatedSubmissions);
        simulationResults.put("questionsPerSubmission", questionsPerSubmission);
        simulationResults.put("totalQuestions", simulatedSubmissions * questionsPerSubmission);
        simulationResults.put("correctAnswers", correctAnswers);
        simulationResults.put("averageScore", avgScore);
        response.put("simulation", simulationResults);

        // Общая информация
        response.put("processors", availableProcessors);
        response.put("totalProcessingTimeMs", endTime - startTime);
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }

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

    @GetMapping("/submissions-simulation")
    @Timed("load.test.submissions")
    public ResponseEntity<Map<String, Object>> simulateSubmissions(
            @RequestParam(defaultValue = "100") int submissions,
            @RequestParam(defaultValue = "10") int questionsPerSubmission) {

        log.info("Симуляция прохождения тестов: прохождений={}, вопросов={}",
                submissions, questionsPerSubmission);

        long startTime = System.currentTimeMillis();
        int correctAnswers = 0;

        for (int i = 0; i < submissions; i++) {
            for (int j = 0; j < questionsPerSubmission; j++) {
                // Имитация процесса проверки ответов
                correctAnswers += random.nextInt(2); // 0 или 1
            }
        }

        long endTime = System.currentTimeMillis();
        double avgScore = (double) correctAnswers / (submissions * questionsPerSubmission);

        Map<String, Object> response = new HashMap<>();
        response.put("submissions", submissions);
        response.put("questionsPerSubmission", questionsPerSubmission);
        response.put("totalQuestions", submissions * questionsPerSubmission);
        response.put("correctAnswers", correctAnswers);
        response.put("averageScore", avgScore);
        response.put("processingTimeMs", endTime - startTime);

        return ResponseEntity.ok(response);
    }
}
