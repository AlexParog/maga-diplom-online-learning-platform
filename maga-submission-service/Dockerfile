FROM maven:3.9-amazoncorretto-17 AS build

# Копируем общие проекты и устанавливаем их
COPY ./online-learning-platform-model /app/online-learning-platform-model
WORKDIR /app/online-learning-platform-model
RUN mvn clean install -DskipTests

# Копируем и собираем зависимые проекты
COPY ./maga-test-service /app/maga-test-service
WORKDIR /app/maga-test-service
RUN mvn clean install -DskipTests -Dmaven.test.skip=true

# Копируем и собираем основной проект
COPY ./maga-submission-service /app/maga-submission-service
WORKDIR /app/maga-submission-service
RUN mvn clean package -DskipTests

# Создаем образ на базе JRE
FROM amazoncorretto:17-alpine

# Метаданные образа
LABEL maintainer="Alexander Bibik"
LABEL description="Submission Service for Online Learning Platform"

# Создаем директорию для приложения
WORKDIR /app

# Копируем собранный JAR
COPY --from=build /app/maga-submission-service/target/*.jar app.jar

# Определение порта
EXPOSE 8094

# Определение точки входа
ENTRYPOINT ["java", "-jar", "app.jar"]