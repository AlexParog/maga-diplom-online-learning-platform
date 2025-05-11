package ru.parog.magatestservice.client;

/**
 * Клиент для взаимодействия с course-service
 */
public interface CourseServiceClient {

    /**
     * Проверяет существование курса по его ID
     *
     * @param courseId ID курса
     * @return true если курс существует, иначе false
     */
    boolean courseExists(Long courseId);

    /**
     * Проверяет существование урока по его ID
     *
     * @param lessonId ID урока
     * @return true если урок существует, иначе false
     */
    boolean lessonExists(Long lessonId);

    /**
     * Получает информацию о курсе по ID
     *
     * @param courseId ID курса
     * @return объект с информацией о курсе или null, если курс не найден
     */
    Object getCourseInfo(Long courseId);

    /**
     * Получает информацию об уроке по ID
     *
     * @param lessonId ID урока
     * @return объект с информацией об уроке или null, если урок не найден
     */
    Object getLessonInfo(Long lessonId);
}
