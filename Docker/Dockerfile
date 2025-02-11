# syntax=docker/dockerfile:1.4
# escape=`


# !! Используйте .dockerignore, чтобы не копировать ненужные файлы ( .git).



# Базовый образ
FROM alpine:latest

# Устанавливаем пакеты и создаем файл
RUN echo "Пример использования parser directives" >> /app.txt `
    && echo "Символ экранирования теперь `` вмdесто \\"

# Копируем файл из хоста в образ
COPY ./file.txt /data/

# Открываем порт
EXPOSE 80

# Команда по умолчанию
CMD ["cat", "/app.txt"]


##############################
# Простой Python-сервер (Flask)
##############################

# Используем официальный образ Python
FROM python:3.9-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем зависимости в контейнер
COPY requirements.txt .

# Устанавливаем зависимости
RUN pip install --no-cache-dir -r requirements.txt

# Копируем остальные файлы проекта
COPY . .

# Открываем порт 5000 (порт Flask по умолчанию)
EXPOSE 5000

# Команда для запуска приложения
CMD ["python", "app.py"]



##############################
# Java + Maven приложение
##############################

# Этап сборки
FROM maven:3.8-jdk-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Этап запуска
FROM openjdk:11-jre-slim
COPY --from=build /app/target/myapp.jar /app/myapp.jar
EXPOSE 8080

CMD ["java", "-jar", "/app/myapp.jar"]




