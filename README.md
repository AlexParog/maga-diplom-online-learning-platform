# Платформа онлайн-обучения с оркестрацией Kubernetes

Проект разработан для магистерской дипломной работы на тему **"Оптимизация производительности веб-приложения через контейнерную оркестрацию с использованием Kubernetes: платформа онлайн-обучения"**.

## Описание проекта

Платформа онлайн-обучения представляет собой микросервисное приложение, развернутое в Kubernetes. Проект демонстрирует сравнение двух подходов к масштабированию:
- Ручное масштабирование через kubectl или Kubernetes Dashboard
- Автоматическое масштабирование с использованием HPA (Horizontal Pod Autoscaler)

### Микросервисы

Приложение состоит из 4 основных микросервисов:

1. **User Service (8091)** - управление пользователями:
   - Регистрация и аутентификация
   - Выдача и валидация JWT-токенов
   - CRUD для пользователей

2. **Course Service (8092)** - управление курсами:
   - CRUD для курсов
   - Структурирование курсов (модули, уроки, тесты)
   - Интеграция с User Service

3. **Test Service (8093)** - управление тестами:
   - CRUD для тестов и вопросов
   - Управление вариантами ответов
   - Интеграция с Course Service

4. **Submission Service (8094)** - обработка ответов на тесты:
   - Прием ответов от студентов
   - Оценивание правильности ответов
   - Сохранение результатов прохождения
   - Подвержен наибольшей нагрузке

## Архитектура

```
+-----------------+    +------------------+    +-----------------+    +---------------------+
|                 |    |                  |    |                 |    |                     |
|  User Service   |--->|  Course Service  |--->|  Test Service   |--->|  Submission Service |
|                 |    |                  |    |                 |    |                     |
+-----------------+    +------------------+    +-----------------+    +---------------------+
        |                      |                      |                         |
        |                      |                      |                         |
        v                      v                      v                         v
+-----------------------------------------------------------------------------------+
|                                  PostgreSQL                                       |
+-----------------------------------------------------------------------------------+
```

## Технический стек

- **Backend**: Java 17, Spring Boot 3.4
- **База данных**: PostgreSQL 15
- **Контейнеризация**: Docker, Kubernetes (Minikube)
- **Мониторинг**:
  - Spring Boot Actuator
  - Prometheus
  - Grafana
- **Нагрузочное тестирование**: Apache JMeter

## Метрики исследования

В рамках исследования сравниваются следующие метрики:

1. Интегральная метрика "ресурсо-часов" (vCPU-часы, GB-часы памяти)
2. CPU Utilization (%) - загрузка процессора подами
3. Memory Utilization (%) - использование памяти
4. Пропускная способность (RPS) - запросы в секунду
5. Уровень ошибок (%) - особенно HTTP 5xx
6. Количество реплик - изменение числа подов во время тестов

## Запуск проекта

### Предварительные требования

- Docker
- Minikube
- kubectl
- JDK 17
- Maven

### Локальный запуск с Docker Compose

```bash
# Сборка проекта
mvn clean package -DskipTests

# Запуск с Docker Compose
docker-compose up -d
```

### Развертывание в Kubernetes (Minikube)

```bash
# Запуск Minikube
minikube start --driver=docker --cpus=6 --memory=9g

# Сборка Docker-образов
eval $(minikube docker-env)
mvn clean package -DskipTests
docker build -t maga-diplom-user-service:latest -f maga-user-service/Dockerfile .
docker build -t maga-diplom-course-service:latest -f maga-course-service/Dockerfile .
docker build -t maga-diplom-test-service:latest -f maga-test-service/Dockerfile .
docker build -t maga-diplom-submission-service:latest -f maga-submission-service/Dockerfile .

# Применение манифестов
kubectl apply -f k8s/postgres.yml
kubectl apply -f k8s/user-deployment.yml
kubectl apply -f k8s/user-service.yml
kubectl apply -f k8s/course-deployment.yml
kubectl apply -f k8s/course-service.yml
kubectl apply -f k8s/test-deployment.yml
kubectl apply -f k8s/test-service.yml
kubectl apply -f k8s/submission-deployment.yml
kubectl apply -f k8s/submission-service.yml
```

## Ручное масштабирование

```bash
# Масштабирование Deployment
kubectl scale deployment submission-service --replicas=3

# Проверка текущего состояния
kubectl get pods
kubectl top pods
```

## Автоматическое масштабирование (HPA)

```bash
# Включение HPA
kubectl apply -f k8s/hpa/submission-service-hpa.yml

# Мониторинг автомасштабирования
kubectl get hpa -w
```

## Визуализация результатов

Результаты исследования доступны через Grafana на порту 3000:

```bash
# Доступ к Grafana
kubectl port-forward svc/grafana 3000:3000
```

## Автор

- **Александр Бибик** - Магистр, направление "DevOps-инженерия"
