package ru.parog.magatestservice.client;

import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseServiceClientImpl implements CourseServiceClient {

    private final RestClient restClient;

    @Value("${services.course-service.url:http://course-service:8092}")
    private String courseServiceUrl;

    @Override
    @Timed("client.course.getCourse")
    @Observed(name = "client.course.getCourse", contextualName = "get-course-data")
    public Object getCourseInfo(Long courseId) {
        log.info("Получение информации о курсе ID: {}", courseId);

        try {
            return restClient.get()
                    .uri(courseServiceUrl + "/api/courses/{courseId}", courseId)
                    .retrieve()
                    .body(Object.class);
        } catch (Exception e) {
            log.error("Ошибка при получении информации о курсе ID {}: {}", courseId, e.getMessage());
            return null;
        }
    }

    @Override
    @Timed("client.course.getLesson")
    @Observed(name = "client.course.getLesson", contextualName = "get-lesson-data")
    public Object getLessonInfo(Long lessonId) {
        log.info("Получение информации о уроке ID: {}", lessonId);

        try {
            return restClient.get()
                    .uri(courseServiceUrl + "/api/lessons/{lessonId}", lessonId)
                    .retrieve()
                    .body(Object.class);
        } catch (Exception e) {
            log.error("Ошибка при получении информации о уроке ID {}: {}", lessonId, e.getMessage());
            return null;
        }
    }

    @Override
    @Timed("client.course.exists")
    @Observed(name = "client.course.exists", contextualName = "check-course-exists")
    public boolean courseExists(Long courseId) {
        log.info("Проверка существования курса ID: {}", courseId);

        if (courseId == null) {
            return false;
        }

        try {
            // Выполняем HEAD запрос для проверки существования ресурса
            restClient.head()
                    .uri(courseServiceUrl + "/api/courses/{courseId}", courseId)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Курс с ID {} не найден", courseId);
                return false;
            }
            log.error("Ошибка при проверке существования курса ID {}: {}", courseId, e.getMessage());
            // В случае других ошибок (например, сервис недоступен), предполагаем, что курс существует
            // чтобы не блокировать работу приложения
            return true;
        } catch (Exception e) {
            log.error("Непредвиденная ошибка при проверке курса ID {}: {}", courseId, e.getMessage());
            return true; // По умолчанию считаем, что курс существует
        }
    }

    @Override
    @Timed("client.lesson.exists")
    @Observed(name = "client.lesson.exists", contextualName = "check-lesson-exists")
    public boolean lessonExists(Long lessonId) {
        log.info("Проверка существования урока ID: {}", lessonId);

        if (lessonId == null) {
            return true; // Null означает, что урок не указан, что допустимо
        }

        try {
            // Выполняем HEAD запрос для проверки существования ресурса
            restClient.head()
                    .uri(courseServiceUrl + "/api/lessons/{lessonId}", lessonId)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Урок с ID {} не найден", lessonId);
                return false;
            }
            log.error("Ошибка при проверке существования урока ID {}: {}", lessonId, e.getMessage());
            return true; // В случае ошибок предполагаем, что урок существует
        } catch (Exception e) {
            log.error("Непредвиденная ошибка при проверке урока ID {}: {}", lessonId, e.getMessage());
            return true; // По умолчанию считаем, что урок существует
        }
    }
}
