# School API :mortar_board:

Test Paper - Part 2

This is a REST API developed in *Java 11* and *Spring Boot 2.5.3*, and implements *JPA-Hibernate* and *JWT*. It 
enables the data management(CRUD) of students, marks and subjects among others.

## Getting started :rocket:

### Requirements :memo:
* JDK 11
* Maven

### Installation :wrench:

Once you have cloned the repository, you can install it on your maven repository by maven command line

``` bash
mvn clean install
```

And then, execute jar file
``` bash
java -jar school-0.0.1-SNAPSHOT.jar
```

### How to use :computer:

The API provides the necessary resources to perform CRUD operations on each entity. It also provides three resources 
for special reports. The resources are listed below.

* *Student's CRUD*
```
GET    /students
GET    /student/{studentId}
PUT    /student/{studentId}
POST   /student
DELETE /student/{studentId}
```
* *Group's CRUD*
```
GET    /groups
GET    /group/{groupId}
PUT    /group/{groupId}
POST   /groups
DELETE /group/{groupId}
```
* *Subject's CRUD*
```
GET    /subjects
GET    /subject/{subjectId}
PUT    /subject/{subjectId}
POST   /groups
DELETE /group/{groupId}
```
* *Mark's CRUD*
```
GET     /marks
GET     /mark/{markId}
PUT     /mark/{markId}
POST    /mark
DELETE  /mark/{markId}
```
* *Subject-teacher CRUD*
```
GET     /subjects/groups
GET     /subject/{subjectId}/group/{groupId}
PUT     /subject/group
POST    /subject/group
DELETE  /subject/{subjectId}/group/{groupId}
```
* *School's special reports*
1. Get the mark for a particular student id
```
/marks/student/{studentId}
```
2. Get the number of students for a particular teacher id
```
/student/{studentId}/subject/{subjectId}
```
3. Get the list of marks in each subject for a particular student id
```
/teacher/{teacherId}/student-count
```
The endpoints mentioned above can be tested locally through Swagger on url http://localhost:8080/swagger-ui.html, 
after executing the steps in the installation section.

## Testing execution :satellite:

*Unit tests*: built with JUnit 5. When you do the first step of the installation section, test are executed. However, to run them independently, you just need to run the following command in terminal
``` bash
mvn test
```
## Key libraries and tools

-   **Java** Programming language.
-   **Spring Data JPA** Persistence layer
-   **H2** In memory database
-   **Spring Security** Enables JWT security
-   **Maven** Build automation tool.
-   **JUnit** Unit testing framework.
-   **Lombok** Java library to avoid write repetitive code.