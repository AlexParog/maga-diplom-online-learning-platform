package ru.parog.magacourseservice.controller;

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
@RequestMapping("/api/course-service/load-test")
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

    @GetMapping("/courses-simulation")
    @Timed("load.test.courses")
    public ResponseEntity<Map<String, Object>> simulateCoursesBrowsing(
            @RequestParam(defaultValue = "100") int users,
            @RequestParam(defaultValue = "5") int coursesPerUser,
            @RequestParam(defaultValue = "3") int modulesPerCourse) {

        log.info("Симуляция просмотра курсов: пользователей={}, курсов={}, модулей={}",
                users, coursesPerUser, modulesPerCourse);

        long startTime = System.currentTimeMillis();
        int totalViews = 0;
        int totalEnrollments = 0;

        for (int i = 0; i < users; i++) {
            for (int j = 0; j < coursesPerUser; j++) {
                // Имитация просмотра курсов и модулей
                totalViews++;

                if (random.nextDouble() < 0.4) { // 40% шанс записи на курс
                    totalEnrollments++;
                }

                for (int k = 0; k < modulesPerCourse; k++) {
                    // Имитация просмотра модулей и уроков
                    totalViews += random.nextInt(3) + 1; // 1-3 урока просмотрено
                }
            }
        }

        long endTime = System.currentTimeMillis();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("coursesPerUser", coursesPerUser);
        response.put("modulesPerCourse", modulesPerCourse);
        response.put("totalCourseViews", totalViews);
        response.put("totalEnrollments", totalEnrollments);
        response.put("processingTimeMs", endTime - startTime);

        return ResponseEntity.ok(response);
    }
}
