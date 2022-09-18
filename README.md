# Description
This project is a test for be BackEnd developer at Elo 7.

### Stack
- Java 17
- Spring Boot
- Spring Doc
- Lombok
- PostgreSQL
- Spring Web
- JPA

# Clone

```bash
# Clone github project
$ git clone https://github.com/heitor582/PlanetsAndProbes.git

# Enter the folder
$ cd ./PlanetsAndProbes
```
# Instalation
## Running the app with docker
```bash
# Iniciate the docker
$ docker-compose up --build -d
```
# Unit Test

```bash
# Unit tests
$ ./gradlew test
```
# Executing OpenApi/Swagger
In the url after run the program access for enter in the swagger and see all the routes with the respectives parameters.
 ```bash
 http://localhost:8080/swagger-ui/index.html
```
# Import archive of Postman
### First what is it Postman?
Postman is a program to make, organize and view the result of api requests.
### Running the app
At the root of the project is a json that contains data that the postman program processes and transforms into pre-made requests.

In the postman click import -> Upload Files

![image](https://user-images.githubusercontent.com/58075535/124396541-92e1f900-dce0-11eb-9a0f-68eed8e69eb7.png)
![image](https://user-images.githubusercontent.com/58075535/124396554-9bd2ca80-dce0-11eb-9ceb-69372af6613f.png)


Import the json that is at the root of the project called 'Elo7.postman_collection.json'
