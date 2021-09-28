# data-logic



![alt text](https://github.com/a2z-ice/spring-data-randd/blob/main/Architecture%20Diagram.jpg)

```shell

# To run the project do the following steps from 4 different terminal:
1. cd eureka-server && ./mvnw spring-boot:run
2. cd api-gateway && ./mvnw spring-boot:run
3. cd project-service && ./mvnw spring-boot:run
4. cd onesearch-service && ./mvnw spring-boot:run


# To get project with paginated data The default page is 0 and size is 5
curl --location --request GET 'http://localhost:8080/project-service/projects/_all?page=0&size=10'

# To create project creation
curl --location --request POST 'http://localhost:8080/project-service/projects/_create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "The test",
    "description": "the description",
    "type": "TYPE-3"
}'

# Delete project
curl --location --request DELETE 'http://localhost:8080/project-service/projects/2/_delete' \




#Update project

curl --location --request PUT 'http://localhost:8080/project-service/projects/1/_update' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "The test iii",
    "description": "updatable false description",
    "status": "DRAFT"
}'

#Search from onesearch-service service
curl --location --request GET 'http://localhost:8080/onesearch-service/_search?query=iiiupdatable'

# Get all sections data
curl --location --request GET 'http://localhost:8080/project-service/sections/_all'

# Create Section data
curl --location --request POST 'http://localhost:8080/project-service/sections/_create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "description selection 2",
    "title": "title selection 2",
    "projectId": 26
}'

# Update section data
curl --location --request PUT 'http://localhost:8080/project-service/sections/26/_update' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "description selection 2",
    "title": "title selecupadte tion 2",
    "projectId": 26
}'

# Delete section data
curl --location --request DELETE 'http://localhost:8080/project-service/sections/2/_delete' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "description selection 2",
    "title": "title selecupadte tion 2",
    "projectId": 26
}'

```

# The project is not fully completed unable to write unite and integration test and also do not do necessary validation
# The assignment require many improvement. Please take a look.