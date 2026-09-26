# 💬 Простой мессенджер для общения

## 📖 О проекте
Реализация мессенджера, аналог Telegram, WhatsApp и других платформ для обмена сообщениями.

## 🛠 Стек технологий
* **Backend:** Spring Framework (Boot, Data, Core)
* **Очереди сообщений:** RabbitMQ
* **Кэширование & Сессии:** Redis
* **База данных:** PostgreSQL
* **Контейнеризация:** Docker / Docker Compose
* **Тестирование:** JUnit, Mockito

---

## ⚙️ Установка

Для клонирования репозитория выполните команду в терминале:
```bash
git clone https://github.com/ixTager/Messenger.git
```

---

## 🚀 Запуск проекта

### 1. Настройка окружения
Перед запуском проекта измените файл `application.yaml` под ваши конфигурации сервера. Пример стандартного `application.yaml`:

```yaml
spring:
  application:
    name: Messenger
  rabbitmq:
    username: guest
    password: guest
    host: localhost
    port: 5672
  data:
    redis:
      host: localhost
      port: 6379
      timeout: 200ms
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: qwerty
  jpa:
    show-sql: true
    hibernate:
      # При необходимости поменяйте на: create-drop
      ddl-auto: update

rabbitmq:
  queues:
    first:
      name: incoming_messages
      routing-key: message.incoming.routing.key
      queue_delay:
        name: delay_queue
    second:
      name: outcoming_messages
      routing-key: message.outgoing.routing.key
      queue_delay:
        name: delay_queue
    status:
      name: message_statues
      routing-key: message.status.routing.key
      queue_delay:
        name: delay_queue

  exchange:
    name: message_exchange

database:
  count:
    last-messages: 20
```

### 2. Запуск инфраструктуры (БД и брокеры)
После настройки конфигурации перейдите в терминале в корневую папку репозитория и запустите контейнеры:
```bash
docker compose up -d
```
*Данная команда скачает необходимые образы (PostgreSQL, Redis, RabbitMQ) и автоматически запустит их в фоновом режиме.*

### 3. Запуск приложения
После успешного поднятия контейнеров запустите главный класс приложения: `AnonymousMessengerApplication.java`.

---

## ❗ Важно
### Перед выполнением вышеперечисленных команд вам необходимо установить [Docker Desktop](https://www.docker.com/get-started/) с официального сайта для вашей операционной системы.