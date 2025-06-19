# King of Adachi

This project is a simple Spring Boot application for a game called "King of Adachi".

## Project Structure

The project follows a layered architecture:

- **presentation**: Controllers and DTOs for handling API requests and responses.
- **application**: Application services and use cases.
- **domain**: Domain entities, value objects, repositories, and domain services.
- **infrastructure**: Implementation of repositories, mappers, and other infrastructure concerns.

## Getting Started

To run the application:

1. Clone the repository.
2. Build the project using Maven: `mvn clean install`
3. Run the application: `java -jar target/kingofadachi-1.0-SNAPSHOT.jar`

The application will be accessible at `http://localhost:8080`. A health check endpoint is available at `http://localhost:8080/health`.

## TODO

- Implement core game logic.
- Add database integration.
- Implement user authentication and authorization.
- Add unit and integration tests.
- Define API specifications (e.g., using OpenAPI).
