# Container Service

A Spring Boot application for managing container bookings.

## Prerequisites

*   Java 17
*   Maven
*   MongoDB

## Installation

The project dependencies are managed by Maven. They will be downloaded automatically when you build the project for the first time.

## Running the application

To run the application, execute the following command:

```bash
./mvnw spring-boot:run
```

The application will start on port 8080.

## Packaging and running from the command line

Alternatively, you can package the application as a JAR file and run it from the command line.

First, package the application using Maven:

```bash
./mvnw package
```

Then, run the application using the `java -jar` command:

```bash
java -jar target/container-service-0.0.1-SNAPSHOT.jar
```

## Running the tests

To run the tests, execute the following command:

```bash
./mvnw test
```

## Assumptions

*   **External API:** The external Maersk API is currently not operational. The application has been designed to work with a mock of this API.
*   **MongoDB:** A running MongoDB instance is required for the application to function correctly.

##  Assistance

An AI-powered coding assistant was used for specific tasks to accelerate development. These tasks included:

*   **Initial Project:** Generating the initial project structure and Maven configuration was done with intellij's new project.
*   **README Generation:** Creating the initial `README.md` file and providing updates.
*   **Test Case Generation:** Generating boilerplate for some of the unit tests.
