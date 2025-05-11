package ru.parog.magauserservice.client;

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
public class SubmissionServiceClient {

    private final RestClient restClient;

    @Value("${services.submission-service.url:http://submission-service:8094}")
    private String submissionServiceUrl;

    @Timed("client.submission.getSubmissions")
    @Observed(name = "client.submission.getSubmissions", contextualName = "get-user-submissions")
    public Object getUserSubmissions(Long userId) {
        log.info("Выборка submissions для пользователя: {}", userId);

        try {
            return restClient.get()
                    .uri(submissionServiceUrl + "/api/users/{userId}/submissions", userId)
                    .retrieve()
                    .body(Object.class);
        } catch (Exception e) {
            log.error("Ошибка при получении ответов для пользователя {}: {}", userId, e.getMessage());
            return null;
        }
    }
}
