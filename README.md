This is a skeleton of Spring Boot application which should be used as a start point to create a working one.
The goal of this task is to create simple web application which allows users to create TODOs. In addition, the application should expose public REST API in order to allow to manipulate data programmatically.

The branch with all features is the feat/Rest-API branch. You will find there the REST API with all the CRUD methods. 
On the launching of the app, a config class will automatically create 2 categories and 2 tasks.
I also made unit tests using JUNIT. My TaskService has 100% coverage on methods, and 93% lines are also covered.

You will find the postman collection in the ressources folder, allowing you to test the creation, reading, update and delete of the tasks.

Improvements possible:

-If the use of the app need it, we can create DTOs and we will not manipulate the entity itself. (Then I rather use a modelmapper for the entity/dto conversion)
-For this example, there was no need to make operations @Transactional 
-The deadline type is timestamp in the database, in Java, i personally would prefer using LocalDateTime (but I still used timestamp in this todo web app)
-Create exception classes and throw them whenever we need. (Ex: "task not found" can be repeated)
