package ru.parog.magatestservice.controller;

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

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/test-service/load-test")
@RequiredArgsConstructor
public class LoadTestController {

    private final MeterRegistry meterRegistry;
    private final Random random = new Random();

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

    @GetMapping("/tests-simulation")
    @Timed("load.test.tests")
    public ResponseEntity<Map<String, Object>> simulateTestsGeneration(
            @RequestParam(defaultValue = "10") int tests,
            @RequestParam(defaultValue = "5") int questionsPerTest,
            @RequestParam(defaultValue = "4") int optionsPerQuestion) {

        log.info("Симуляция генерации тестов: тестов={}, вопросов={}, вариантов={}",
                tests, questionsPerTest, optionsPerQuestion);

        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> generatedTests = new ArrayList<>();

        for (int i = 0; i < tests; i++) {
            Map<String, Object> test = new HashMap<>();
            test.put("id", i + 1);
            test.put("title", "Тест " + (i + 1));
            test.put("description", "Описание теста " + (i + 1));

            List<Map<String, Object>> questions = new ArrayList<>();
            for (int j = 0; j < questionsPerTest; j++) {
                Map<String, Object> question = new HashMap<>();
                question.put("id", j + 1);
                question.put("text", "Вопрос " + (j + 1));

                List<Map<String, Object>> options = new ArrayList<>();
                int correctOption = random.nextInt(optionsPerQuestion);

                for (int k = 0; k < optionsPerQuestion; k++) {
                    Map<String, Object> option = new HashMap<>();
                    option.put("id", k + 1);
                    option.put("text", "Вариант " + (k + 1));
                    option.put("isCorrect", k == correctOption);
                    options.add(option);
                }

                question.put("options", options);
                questions.add(question);
            }

            test.put("questions", questions);
            generatedTests.add(test);
        }

        long endTime = System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("tests", tests);
        response.put("questionsPerTest", questionsPerTest);
        response.put("optionsPerQuestion", optionsPerQuestion);
        response.put("totalQuestions", tests * questionsPerTest);
        response.put("totalOptions", tests * questionsPerTest * optionsPerQuestion);
        response.put("processingTimeMs", endTime - startTime);

        return ResponseEntity.ok(response);
    }
}
