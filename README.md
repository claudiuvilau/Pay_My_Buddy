# spring-boot

## Technical

1. Framework: Spring Boot v3.0.6
2. Java 17
3. Thymeleaf

## Setup

1. Create project
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Resources: src/main/resource/diagramme_image
    - Resources: src/main/resource/templates
    - Static: src/main/resource/static
4. Create database with name "paymybuddy" as configuration in application.properties
5. Run sql script to create table src/main/resource/createDataBaseAndTables.sql

## Diagram

1. [diagram class UML](src/main/resources/diagramme_image/paymybuddyUML.jpg)
2. [physical data model](src/main/resources/diagramme_image/paymybuddyMLD.jpg)

## Implement a Feature

1. Create mapping domain class and place in package com.openclassrooms.pay_my_buddy.model
2. Create repository class and place in package com.openclassrooms.pay_my_buddy.repository
3. Create controller class and place in package com.openclassrooms.pay_my_buddy.controller
4. Create view files and place in src/main/resource/templates

## Write Unit Test

1. Create unit test and place in package com.openclassrooms.pay_my_buddy in folder test > java

## Security

1. Create user service to load user from  database and place in package com.openclassrooms.pay_my_buddy.service
2. Add configuration class and place in package com.openclassrooms.pay_my_buddy.configuration
