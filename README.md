# Container Service

A Spring Boot application for managing container bookings.

## Prerequisites

*   Java 17
*   Maven
*   MongoDB (4.0 or higher)

## MongoDB Configuration

The application requires a running MongoDB instance. Default configuration expects:
- Host: `localhost:27017`
- Database: `container-service`
- Username: `admin`
- Password: `admin123`
- Auth Database: `admin`

### Configuring MongoDB Credentials

**Option 1: Edit application.properties**

Update `src/main/resources/application.properties` with your MongoDB credentials.

**Option 2: Use Environment Variables**

```bash
export MONGODB_HOST=your_host
export MONGODB_USERNAME=your_username
export MONGODB_PASSWORD=your_password
./mvnw spring-boot:run
```

**Option 3: Command-line Arguments**

```bash
java -jar target/container-service-0.0.1-SNAPSHOT.jar \
  --spring.data.mongodb.host=your_host \
  --spring.data.mongodb.username=your_username \
  --spring.data.mongodb.password=your_password
```

## Running the Application

Maven will automatically download all dependencies on first build.

**Run with Maven:**

```bash
./mvnw spring-boot:run
```

**Or build and run as JAR:**

```bash
./mvnw package
java -jar target/container-service-0.0.1-SNAPSHOT.jar
```

The application will start on port 8080.

## Running Tests

```bash
./mvnw test
```

## Notes

- The external Maersk API endpoint is mocked for testing purposes
- A running MongoDB instance with proper credentials is required for the application to function
