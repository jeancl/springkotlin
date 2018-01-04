# About springkotlin project
Basic model of a microservice ready RESTful api using Spring Boot + Spring Data MongoDB + Kotlin +  Maven

This is the source code for a study project of Spring Boot application developed with Kotlin and Mongo Spring Data JPA.

##Running
You can launch the application by running maven and then running "java -jar springkotlin-0.0.1-SNAPSHOT.jar" on generated .jar file

Make sure you have at least IntelliJ IDEA 2017.1 and Kotlin plugin 1.1.x. This project uses a Kotlin based Maven configuration.

After running the Spring Boot, test in your browser opening the URL: http://localhost:8080/api/movies

This project is Apache 2.0 licensed.

##Extra BackboneJS front-end

An example of front-end that consumes the movies api can be found inside folder webapp>BackBoneJsApp.
After running the Spring Boot open index.html in your browser. Should look like the image below.

![MoviesApp](http://keycodetech.com/wp-content/uploads/2018/01/Screen-Cap-MoviesApp.png)

