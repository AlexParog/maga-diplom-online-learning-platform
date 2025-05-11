package ru.parog.magasubmissionservice.client;

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
public class UserServiceClient {

    private final RestClient restClient;

    @Value("${services.user-service.url:http://user-service:8091}")
    private String userServiceUrl;

    @Timed("client.user.validateToken")
    @Observed(name = "client.user.validateToken", contextualName = "validate-token")
    public Boolean validateToken(String token) {
        log.info("Валидация auth токена");

        try {
            return restClient.get()
                    .uri(userServiceUrl + "/api/auth/validate")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(Boolean.class);
        } catch (Exception e) {
            log.error("Ошибка при валидации auth токена: {}", e.getMessage());
            return false;
        }
    }

    @Timed("client.user.updateStatistics")
    @Observed(name = "client.user.updateStatistics", contextualName = "update-user-statistics")
    public void updateUserStatistics(Long userId, Object statisticsData) {
        log.info("Обновление статистики для пользователя ID: {}", userId);

        try {
            restClient.put()
                    .uri(userServiceUrl + "/api/users/{userId}/statistics", userId)
                    .body(statisticsData)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.error("Ошибка при обновлении статистики для пользователя ID {}: {}", userId, e.getMessage());
        }
    }
}
