                                                       ОПИСАНИЕ ПРОЕКТА

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

                                                     АРХИТЕКТУРА СЕРВИСА
                                                      
Все эндпойнты, за исключением /api/v1/auth/ доступны только авторизированным пользователям. Когда пользователь обращается к /api/v1/data/orders(POST) для создания заказа, создаётся имя клиента на основании jwt токена текущего пользователя, после чего создаётся заказ с указанной суммой. Имя клиента для пользователя может быть только одно, которое можно изменить по эндпойнту /api/v1/update/clients(POST). Заказов у клиента может быть множество, которые также можно изменить.


                                                      REST API ENDPOINTS
                                                      
         
         
  AUTHENTICATION ENDPOINTS(/api/v1/auth/)
  
  - /api/v1/auth/login:
  
        {
        "username":"",
        "password":""
        }
        
  - /api/v1/auth/signUp:
  
        {
        "username":"",
        "password":"",
        "email":""
        }
        
  - /api/v1/auth/logout



DATA ENDPOINTS(/api/v1/data/)

- /api/v1/data/orders(POST):

        {
         "price": ,
         "clientName": ""
        }

- /api/v1/data/orders(GET):
 
- /api/v1/data/users(GET):

- /api/v1/data/orders/clients/{id}(GET):
 
- /api/v1/data/clients/{id}(GET):
        
- /api/v1/data/clients(GET):



UPDATE ENDPOINTS(/api/v1/update/)
                                                  
- /api/v1/update/clients(POST):

        {
         "name": ""
        }

- /api/v1/update/orders(POST):

        {
         "id": ,
         "price": 
        }
        
- /api/v1/update/users/username(POST):

        {
         "username": ""
        }
        
- /api/v1/update/users/email(POST):

        {
         "email": ""
        }
