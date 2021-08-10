# School API :mortar_board:

This is a REST API developed in *Java 11* and *Spring Boot 2.5.3*, and implements *JPA-Hibernate* and *JWT*. It 
enables the data management(CRUD) of students, marks and subjects among others.

## Getting started :rocket:

### Requirements :memo:
* JDK 11
* Maven

### Installation :wrench:

Once you have cloned the repository, you can install it on your maven repository by maven command line

```
mvn clean install
```

And then, execute jar file
```
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
* *Student's special reports*
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