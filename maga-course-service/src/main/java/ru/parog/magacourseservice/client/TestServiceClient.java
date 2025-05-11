package ru.parog.magacourseservice.client;

import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestServiceClient {

    private final RestClient restClient;

    @Value("${services.test-service.url:http://test-service:8093}")
    private String testServiceUrl;

    @Timed("client.test.getTests")
    @Observed(name = "client.test.getTests", contextualName = "get-tests-for-course")
    public Object getTestsForCourse(Long courseId) {
        log.info("Получение тестов для курса ID: {}", courseId);

        try {
            return restClient.get()
                    .uri(testServiceUrl + "/api/courses/{courseId}/tests", courseId)
                    .retrieve()
                    .body(Object.class);
        } catch (Exception e) {
            log.error("Ошибка при получении тестов для курса ID {}: {}", courseId, e.getMessage());
            return null;
        }
    }

    @Timed("client.test.getLessonTest")
    @Observed(name = "client.test.getLessonTest", contextualName = "get-test-for-lesson")
    public Object getTestForLesson(Long lessonId) {
        log.info("Получение теста для урока ID: {}", lessonId);

        try {
            return restClient.get()
                    .uri(testServiceUrl + "/api/lessons/{lessonId}/test", lessonId)
                    .retrieve()
                    .body(Object.class);
        } catch (Exception e) {
            log.error("Ошибка при получении теста для урока ID {}: {}", lessonId, e.getMessage());
            return null;
        }
    }
}
