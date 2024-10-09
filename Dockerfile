# Используем базовый образ JDK для сборки
FROM bellsoft/liberica-openjdk-alpine:21 AS build

# Копируем весь проект в контейнер
COPY . /app

# Переходим в директорию с приложением
WORKDIR /app

# Сборка JAR файла
RUN ./gradlew clean bootJar

# Используем минимальный JDK образ для запуска приложения
FROM bellsoft/liberica-openjdk-alpine:21

# Создаем пользователя для безопасности
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Копируем собранный JAR файл из предыдущего шага
COPY --from=build /app/build/libs/*.jar app.jar

# Запуск приложения
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "/app.jar"]