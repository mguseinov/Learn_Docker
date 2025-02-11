# 📌 Docker Compose Documentation

## 🔹 Что такое Docker Compose?
Docker Compose — это инструмент для **упрощённого управления многоконтейнерными приложениями** с помощью единого YAML-файла (`docker-compose.yml`). Позволяет запускать, останавливать и управлять сервисами с помощью одной команды.


## 🔹 Базовая структура `docker-compose.yml`
Простейший пример `docker-compose.yml` для запуска **Nginx и MySQL**:
```yaml
version: '3.8'
services:
  web:
    image: nginx:latest
    ports:
      - "8080:80"
  db:
    image: mysql:5.7
    environment:
      MYSQL_ROOT_PASSWORD: example
```

## 🔹 Основные команды
Запуск всех сервисов в фоновом режиме:
```sh
docker-compose up -d
```
Остановка всех контейнеров:
```sh
docker-compose down
```
Перезапуск сервисов:
```sh
docker-compose restart
```
Просмотр логов:
```sh
docker-compose logs -f
```
Список работающих контейнеров:
```sh
docker-compose ps
```

## 🔹 Разбор параметров `docker-compose.yml`
```yaml
version: '3.8'  # Версия Docker Compose
services:
  app:  # Имя сервиса
    image: python:3.9  # Используемый образ
    build: .  # Строит образ из Dockerfile в текущей директории
    ports:
      - "5000:5000"  # Проброс портов (хост:контейнер)
    volumes:
      - ./app:/app  # Маунтинг директорий
    environment:
      - ENV=production  # Указание переменных среды
    depends_on:
      - db  # Зависимость от другого сервиса
  db:
    image: postgres:13
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
```

## 🔹 Переменные окружения
Можно вынести параметры в файл **`.env`**:
```sh
DB_USER=user
DB_PASS=secret
```
И использовать в `docker-compose.yml`:
```yaml
environment:
  POSTGRES_USER: ${DB_USER}
  POSTGRES_PASSWORD: ${DB_PASS}
```

## 🔹 Масштабирование сервисов
Можно запустить несколько экземпляров сервиса:
```sh
docker-compose up --scale app=3 -d
```

## 🔹 Использование в продакшене
Для продакшен-среды лучше использовать **docker-compose.override.yml** или **Swarm Mode** (`docker stack deploy`).

## 🔹 Полезные команды
Удаление контейнеров и томов:
```sh
docker-compose down -v
```
Пересборка образов:
```sh
docker-compose up --build
```





## 🔹 Дополнительные примеры `docker-compose.yml`

### 📌 Пример 1: Node.js + MongoDB
```yaml
version: '3.8'
services:
  app:
    image: node:16
    working_dir: /app
    volumes:
      - ./app:/app
    ports:
      - "3000:3000"
    depends_on:
      - mongodb
  mongodb:
    image: mongo:latest
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: example
    ports:
      - "27017:27017"
```
📌 **Объяснение:** Этот файл запускает **Node.js приложение**, которое использует **MongoDB** как базу данных.

---

### 📌 Пример 2: WordPress + MySQL
```yaml
version: '3.8'
services:
  wordpress:
    image: wordpress:latest
    ports:
      - "8080:80"
    environment:
      WORDPRESS_DB_HOST: db
      WORDPRESS_DB_USER: user
      WORDPRESS_DB_PASSWORD: password
      WORDPRESS_DB_NAME: wordpress
    depends_on:
      - db
  db:
    image: mysql:5.7
    environment:
      MYSQL_DATABASE: wordpress
      MYSQL_USER: user
      MYSQL_PASSWORD: password
      MYSQL_ROOT_PASSWORD: rootpassword
    volumes:
      - db_data:/var/lib/mysql
volumes:
  db_data:
```
📌 **Объяснение:** Этот файл развертывает **WordPress** с базой данных **MySQL** и сохраняет данные в том (`db_data`).

## 🔹 Заключение
Docker Compose позволяет **легко управлять многоконтейнерными приложениями**, избавляя от необходимости вручную запускать каждый контейнер. Используйте его для локальной разработки, тестирования и даже в небольших продакшен-системах.



