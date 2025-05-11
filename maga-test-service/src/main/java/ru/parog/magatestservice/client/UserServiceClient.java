package ru.parog.magatestservice.client;

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

    @Timed("client.user.getUser")
    @Observed(name = "client.user.getUser", contextualName = "get-user-data")
    public Object getUserInfo(Long userId) {
        log.info("Получение информации о пользователе ID: {}", userId);

        try {
            return restClient.get()
                    .uri(userServiceUrl + "/api/users/{userId}", userId)
                    .retrieve()
                    .body(Object.class);
        } catch (Exception e) {
            log.error("Ошибка при получении информации о пользователе ID {}: {}", userId, e.getMessage());
            return null;
        }
    }
}
