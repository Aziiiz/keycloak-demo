## Keycloak Spring Boot Application
This repository contains a Spring Boot application designed to test Keycloak features. The primary goal is to demonstrate and evaluate the functionalities and security provided by Keycloak. The application consists of two main RESTful APIs: one for admins and another for users.

* Table of Contents
  * Features
  * Requirements
  * Installation
  * Configuration
  * Usage
  * API Endpoints

  <br>
* Integration with Keycloak for authentication and authorization
Two separate APIs for admin and user roles
Demonstrates role-based access control (RBAC)
Comprehensive test coverage

* Requirements
  * JDK 11 or higher
  * Maven 3.6.3 or higher
  * Keycloak 15.0.2 or higher

* Installation
  * Clone the repository:
    `git clone https://github.com/yourusername/keycloak-spring-boot-testing.git`
  * Navigate to the project directory:
    `cd keycloak-spring-boot-testing`
  * Build the project using Maven: `mvn clean install`
  
  <br>
* Configuration<br/>
Set up Keycloak following the official documentation.
Create a new realm, clients, and roles (admin and user) in Keycloak.
Configure the application by updating the src/main/resources/application.yml file with your Keycloak server details, client ID, and client secret.
* Usage<br/>
Run the application using Maven:
`mvn spring-boot:run` <br/>
The application will start on http://localhost:8000.

* API Endpoints <br/>
The application provides the following RESTful API endpoints:

  * Admin API (accessible only by users with the admin role) <br/>
  `GET /api//v1/admin`: Retrieve admin-specific data
  * User API (accessible by users with the user role) <br/>
  `GET /api/v1/user`: Retrieve user-specific data