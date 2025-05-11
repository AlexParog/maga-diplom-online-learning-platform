package ru.parog.magasubmissionservice.client;

import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestServiceClient {

    private final RestClient restClient;

    @Value("${services.test-service.url:http://test-service:8093}")
    private String testServiceUrl;

    @Timed("client.test.getQuestions")
    @Observed(name = "client.test.getQuestions", contextualName = "get-questions-for-test")
    public List<Map<String, Object>> getQuestionsByTestId(Long testId) {
        log.info("Получение вопросов для теста ID: {}", testId);

        try {
            return restClient.get()
                    .uri(testServiceUrl + "/api/tests/{testId}/questions", testId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            log.error("Ошибка при получении вопросов для теста ID {}: {}", testId, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Timed("client.test.getOptions")
    @Observed(name = "client.test.getOptions", contextualName = "get-options-for-question")
    public List<Map<String, Object>> getOptionsByQuestionId(Long questionId) {
        log.info("Получение вариантов ответов для вопроса ID: {}", questionId);

        try {
            return restClient.get()
                    .uri(testServiceUrl + "/api/questions/{questionId}/options", questionId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            log.error("Ошибка при получении вариантов ответов для вопроса ID {}: {}", questionId, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Timed("client.test.getTest")
    @Observed(name = "client.test.getTest", contextualName = "get-test-data")
    public Map<String, Object> getTestById(Long testId) {
        log.info("Получение информации о тесте ID: {}", testId);

        try {
            return restClient.get()
                    .uri(testServiceUrl + "/api/tests/{testId}", testId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {
                    });
        } catch (Exception e) {
            log.error("Ошибка при получении информации о тесте ID {}: {}", testId, e.getMessage());
            return Collections.emptyMap();
        }
    }
}
