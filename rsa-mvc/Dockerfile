# Указываем образ, от которого наследуемся
FROM gradle:7.2.0-jdk16

# ARG JAR_FILE=build/libs/*.jar
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} rsa-mvc-image.jar

# Устанавливаем рабочую директорию
# WORKDIR /project-rsa-mvc
# Копируем директорию из основной файловой системы внутрь образа
# COPY ./project-rsa-mvc .

# Определяет действие, которое будет выполнено при старте контейнера (docker run)
# Используется только в случае, если контейнер был запущен без указания команды
# [команда, аргумент]
CMD ["echo", "Hello"]

# Сообщаем, что контейнер использует 8000 порт
EXPOSE 3000

ENTRYPOINT ["java", "-jar", "rsa-mvc-image.jar"]
