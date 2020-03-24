# My goods
Get your music CDs collection under control!

## Table of contents
* [General info](#general-info)
* [User stories](#user-stories)
* [Technologies](#technologies)
* [Used API](#used-API)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
The app will allow logged users to store their music CDs collection. CDs can be found by entering keywords (artist, title).

## User stories
* User can create his own account.
* User can browse music CD inputting query - artist and title.
* User can see detailed info about album browsed.
* User after logging can see top albums of his fav genres on main page.
* User can use his own tags to mark albums.

## Technologies
* Spring Boot 2.2.5
* Java 13
* PostgreSQL 12
* Docker
* Swagger 2

## Used API
* [Last.fm API](https://www.last.fm/api/)

## Setup

### Using Gradle, JAVA and PostgreSQL
In application-production.properties please insert your datasource credentials (username, password and URL), API key and JWT secret.

Build application
```
$ gradle clean build
```
Run application
```
$ java -jar -Dspring.profiles.active=production build/libs/*.jar 
```

### Using Docker and docker-compose
In application-production.properties please insert your datasource credentials (username, password and URL), API key and JWT secret.
In docker-compose.yml please insert your datasource credentials (DB, user, password and URL)

Build application
```
$ gradle clean build
```
In build folder create dependency directory.
```
mkdir dependency
```

In dependency directory extract fatJar from build/libs directory
```
cd dependency 
jar -xf ../libs/*.jar
```

Run with docker-compose
```
docker-compose build
docker-compose up
```


## Features
Work in progress.

## Status
Project is in progress.

## Inspiration
https://dev.to/cuongld2/create-apis-with-jwt-authorization-using-spring-boot-24f9

https://www.baeldung.com/spring-boot-testing

https://springframework.guru/using-resttemplate-in-spring/

https://www.youtube.com/watch?v=gduKpLW_vdY

https://www.youtube.com/watch?v=8s9I1G4tXhA

https://sztukakodu.pl/jak-definiowac-kody-http-odpowiedzi-w-springu/

https://github.com/Baeldung/spring-security-registration - and linked tutorials

https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/

https://www.baeldung.com/gradle-fat-jar

https://www.baeldung.com/jpa-tagging-advanced

https://blog.codeleak.pl/2020/03/spring-boot-docker-compose.html

https://www.youtube.com/watch?v=YFl2mCHdv24

https://www.youtube.com/watch?v=Qw9zlE3t8Ko


## Contact
Created by [Marta Słysz](https://github.com/MartaSlysz) during FairIT - training and recruitment program for women.
If you want to know more about FairIT, here is [website](https://www.fairit.pl/) and [Linkedin](https://www.linkedin.com/company/fairit-trojmiasto/).
