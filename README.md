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
* User can browse music CD inputting query - set of artist and title or musicbrainzid.
* User can see detailed info about album browsed.
* User can use his own tags to mark albums.

## Technologies
* Spring Boot 2.2.5
* Java 13
* PostgreSQL 12
* Docker
* Swagger 2
* Flyway

## Used API
* [Last.fm API](https://www.last.fm/api/)

## Setup (using Docker and docker-compose)
You have to:
- have Docker and docker-compose installed on your computer
- provide [Last.fm API](https://www.last.fm/api/) key and JWT secret - for example generated strong password.

```
$ ./run.sh LASTFM_API_KEY JWT_SECRET
```
For landing page - see http://localhost:8082

For Swagger documentation - see http://localhost:8082/swagger-ui.html

## Features
* Browse music CD using artist and title.
* Browse music CD using musicbrainzid.
* Save album to database.
* See tracklist of saved album.
* Mark albums with your own tags.

## Status
It works but I am working on some improvements 

## Inspiration
* https://blog.codeleak.pl/2017/09/lombok-you-should-definitely-give-it-try.html

* https://dev.to/cuongld2/create-apis-with-jwt-authorization-using-spring-boot-24f9

* https://springframework.guru/using-resttemplate-in-spring/

* https://www.youtube.com/watch?v=gduKpLW_vdY

* https://www.youtube.com/watch?v=8s9I1G4tXhA

* https://github.com/swagger-api/swagger-core/wiki/Annotations

* https://sztukakodu.pl/jak-definiowac-kody-http-odpowiedzi-w-springu/

* https://blog.codeleak.pl/2013/04/spring-mvc-pathvariable-tips.html

* https://blog.codeleak.pl/2016/09/injecting-authenticated-user-into.html

* https://github.com/Baeldung/spring-security-registration - and linked tutorials

* https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/

* https://www.baeldung.com/jpa-tagging-advanced

* https://blog.codeleak.pl/2020/03/spring-boot-docker-compose.html

* https://www.youtube.com/watch?v=YFl2mCHdv24

* https://www.youtube.com/watch?v=Qw9zlE3t8Ko

* https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/

* https://vladmihalcea.com/jpa-hibernate-synchronize-bidirectional-entity-associations/

## Contact
Created by [Marta Słysz](https://github.com/MartaSlysz) during FairIT - training and recruitment program for women.
If you want to know more about FairIT, here is [website](https://www.fairit.pl/) and [Linkedin](https://www.linkedin.com/company/fairit-trojmiasto/).

I would also like to thank my coding mentor - [Rafał Borowiec](https://github.com/kolorobot) - for his advices and patience.
Rafał also keeps a [Codeleak Blog](https://blog.codeleak.pl) with many useful articles.
