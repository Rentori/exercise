Описание проекта

Дается проект сгенерирован на https://start.spring.io/ с заданной конфигурацией.

Требуется создать БД со следующими таблицами: 

- пользователи (id, email, пароль, дата создания);
- клиенты (id, имя, дата создания);
- заказы (id, id клиента, стоимость, дата создания);

Написать API, которое под /data/** выдает данные из таблицы заказов и клиентов (в формате JSON), а под /update/** обновляет данные в таблицах (например обновление имени клиента или смена id клиента, указанного в заказе).  Данные операции должны допускаться только авторизируемым пользователям. Пользователь считается авторизированным, если он делает HTTP запрос:

- с Bearer токеном (указан на сервере)
- с JWT токеном (назначается клиенту сервером на основе логина+пароля)

Способы авторизации зависят от пути: нужна Bearer авторизация для запросов под /update/** и JWT авторизация для запросов под /data/**.

Ограничения:
- Для авторизации использовать модуль spring security.
- Использовать либо JDBC, либо JPA для запросов (оба содержатся в зависимостях проекта)
- Выход из системы (logout) необходим
- Версия 8 Джавы

Допускается:
- Менять архитектуру проекта любым способом
- Добавлять зависимости в проект
- Создавать дополнительные поля в таблицы БД

Приветствуется:
- Логирование через logback
- Аргументированный подход по созданию архитектуры проекта
- Дополнительные запросы и функционал
