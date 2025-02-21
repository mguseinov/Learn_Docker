docker logs
docker ps -a

docker build 
docker run  

#запускает образ

### **Основные команды в Dockerfile** 🚀

**Dockerfile** — это инструкция, по которой Docker собирает образ контейнера. В нем используются директивы (команды), каждая из которых выполняет определенную задачу. 

---

## **1. FROM** (Базовый образ)
```dockerfile
FROM ubuntu:latest
```
- **Обязательно первая строка** в Dockerfile.
- Определяет базовый образ, на основе которого будет собираться контейнер.
- Можно использовать **официальные образы** из **Docker Hub** (например, `alpine`, `ubuntu`, `node`).

✅ **Пример:**
```dockerfile
FROM python:3.9-alpine
```
---

## **2. WORKDIR** (Рабочая директория)
```dockerfile
WORKDIR /app
```
- Устанавливает **текущую рабочую директорию** внутри контейнера.
- Все следующие команды `COPY`, `RUN`, `CMD` будут выполняться в этой директории.

✅ **Пример:**
```dockerfile
FROM ubuntu
WORKDIR /usr/src/app
```

---

## **3. COPY** (Копирование файлов в контейнер)
```dockerfile
COPY . /app
```
- Копирует файлы из **локальной папки (host)** в контейнер.
- `COPY <источник> <назначение>`.

✅ **Пример:**
```dockerfile
COPY my_script.py /app/script.py
```

---

## **4. ADD** (Копирование + Распаковка)
```dockerfile
ADD archive.tar.gz /app
```
- Работает аналогично `COPY`, но может:
  - **Распаковывать** архивы `.tar.gz`.
  - **Загружать файлы по URL**.

✅ **Пример:**
```dockerfile
ADD https://example.com/data.zip /data
```
*📌 `ADD` лучше использовать только для скачивания или распаковки, иначе предпочтителен `COPY`.*

---

## **5. RUN** (Выполнение команд в контейнере)
```dockerfile
RUN apt update && apt install -y curl
```
- Запускает команды **на этапе сборки образа**.
- Обычно используется для установки пакетов.

✅ **Пример:**
```dockerfile
RUN pip install flask
```

---

## **6. CMD** (Команда по умолчанию при запуске контейнера)
```dockerfile
CMD ["python", "app.py"]
```
- **Определяет команду**, которая будет выполняться при запуске контейнера.
- **Используется только один раз**, если есть несколько `CMD`, выполняется последняя.
- При желании можно **переопределить** команду при запуске:
  ```sh
  docker run my_app python another_script.py
  ```

✅ **Пример:**
```dockerfile
CMD ["nginx", "-g", "daemon off;"]
```

---

## **7. ENTRYPOINT** (Фиксированная команда для контейнера)
```dockerfile
ENTRYPOINT ["python", "app.py"]
```
- **Не заменяется при запуске контейнера** (в отличие от `CMD`).
- Можно добавить аргументы при запуске.

✅ **Пример:**
```dockerfile
ENTRYPOINT ["echo", "Hello from Docker!"]
```
При запуске:
```sh
docker run my_app
```
Выведет:
```
Hello from Docker!
```

*📌 `CMD` можно переопределять, а `ENTRYPOINT` — нет. Их можно комбинировать!*

✅ **Пример сочетания `ENTRYPOINT` и `CMD`**:
```dockerfile
ENTRYPOINT ["python"]
CMD ["app.py"]
```
При запуске контейнера выполняется:
```sh
python app.py
```
Но если запустить с аргументом:
```sh
docker run my_app another_script.py
```
То выполнится:
```sh
python another_script.py
```

---

## **8. ENV** (Переменные окружения)
```dockerfile
ENV APP_ENV=production
```
- Устанавливает **переменные окружения** внутри контейнера.

✅ **Пример:**
```dockerfile
ENV PORT=8080
```
При запуске контейнера:
```sh
echo $PORT
```
Выведет:
```
8080
```

---

## **9. ARG** (Переменные для сборки образа)
```dockerfile
ARG APP_VERSION=1.0
```
- **Используется только во время сборки образа**, в отличие от `ENV`.
- Можно передавать аргументы через `docker build`:
  ```sh
  docker build --build-arg APP_VERSION=2.0 .
  ```

✅ **Пример:**
```dockerfile
ARG USER_NAME=admin
RUN echo "User: $USER_NAME"
```

---

## **10. EXPOSE** (Открытие порта в контейнере)
```dockerfile
EXPOSE 8080
```
- **Декларативная инструкция**, которая говорит, какой порт будет использоваться внутри контейнера.
- Сам по себе **не открывает порт** на хосте! Нужно дополнительно указать `-p` при запуске:
  ```sh
  docker run -p 8080:8080 my_app
  ```

✅ **Пример:**
```dockerfile
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

---

## **11. VOLUME** (Создание томов)
```dockerfile
VOLUME /data
```
- Создает **папку внутри контейнера**, которая будет сохраняться после удаления контейнера.

✅ **Пример:**
```dockerfile
VOLUME /var/lib/mysql
```
- Теперь данные MySQL сохранятся после остановки контейнера.

---

## **12. HEALTHCHECK** (Проверка состояния контейнера)
```dockerfile
HEALTHCHECK --interval=30s --timeout=10s \
  CMD curl -f http://localhost:8080 || exit 1
```
- Проверяет, работает ли приложение внутри контейнера.
- Если команда **возвращает ошибку**, контейнер считается **"неисправным"**.

✅ **Пример:**
```dockerfile
HEALTHCHECK CMD curl --fail http://localhost:80 || exit 1
```

---

## **13. LABEL** (Метаинформация)
```dockerfile
LABEL version="1.0"
LABEL maintainer="your-email@example.com"
```
- Добавляет метаинформацию к образу.

✅ **Пример:**
```dockerfile
LABEL description="My Web App"
```

---

## **14. USER** (Запуск контейнера от имени пользователя)
```dockerfile
USER 1001
```
- Меняет пользователя, под которым будут выполняться команды в контейнере.
- **Лучше не запускать контейнеры от root**.

✅ **Пример:**
```dockerfile
RUN useradd -m appuser
USER appuser
```

---

## **15. ONBUILD** (Автоматическое выполнение команд в дочерних образах)
```dockerfile
ONBUILD COPY . /app
```
- **Выполняется ТОЛЬКО при наследовании образа** в другом `Dockerfile`.

✅ **Пример:**
```dockerfile
FROM python:3.9
ONBUILD COPY . /app
```
При использовании этого образа в другом `Dockerfile`, `COPY` выполнится автоматически.

---

## **16. SHELL** (Выбор интерпретатора команд)
```dockerfile
SHELL ["powershell", "-Command"]
```
- Меняет интерпретатор команд (по умолчанию `/bin/sh` в Linux).

✅ **Пример:**
```dockerfile
SHELL ["cmd", "/S", "/C"]
```
- Используется для Windows-контейнеров.

---

## **Вывод**
| Команда      | Описание |
|--------------|--------------------------------|
| **FROM**     | Указывает базовый образ |
| **WORKDIR**  | Устанавливает рабочую директорию |
| **COPY**     | Копирует файлы в контейнер |
| **ADD**      | Копирует файлы (может распаковывать) |
| **RUN**      | Запускает команды при сборке |
| **CMD**      | Команда по умолчанию при запуске |
| **ENTRYPOINT** | Фиксированная команда контейнера |
| **ENV**      | Устанавливает переменные окружения |
| **EXPOSE**   | Объявляет порты контейнера |
| **VOLUME**   | Создает постоянное хранилище |
| **HEALTHCHECK** | Проверяет состояние контейнера |

Теперь вы знаете все ключевые команды **Dockerfile**! 🚀