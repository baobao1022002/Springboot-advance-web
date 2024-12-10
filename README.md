## 1. Project Setup, Spring Boot Basics, and Dependency Injection

- **Objective**: Set up the Spring Boot project and understand core concepts, including dependency injection.

- **Topics**:
  - **Project Initialization**:
    - Create a new Spring Boot project using Spring Initializr.
    - Understand the project structure and key dependencies.
  - **Spring Boot Basics**:
    - Explore annotations like `@SpringBootApplication`, `@RestController`, and `@RequestMapping`.
    - Create a simple REST controller that returns a greeting: `GET /hello-world`
  - **Dependency Injection (DI)**:
    - Learn how Spring manages dependencies.
    - Understand the use of `@Component`, `@Service`, and `@Autowired`.

- **Tasks**:
  - Set up the development environment (IDE, JDK).
  - Initialize a Git repository for version control.
  - Implement a REST endpoint using `@RestController`.
  - Create a service class and inject it into the controller using DI.

- **Check-in Questions**:
  1. What is the purpose of the `@SpringBootApplication` annotation?
  2. How does dependency injection improve the modularity of your code?
  3. Explain how `@Autowired` works in Spring Boot.

---

## 2. Database Integration with JPA and MySQL, Generics, and Validation

- **Objective**: Integrate a MySQL database using Spring Data JPA, apply generics, and implement input validation.

- **Topics**:
  - **Database Integration**:
    - Set up a local or Dockerized MySQL database.
    - Configure database connection settings in `application.properties`.
    - Create entity classes with `@Entity` and repositories with `@Repository`.
  - **Spring Data JPA**:
    - Use `CrudRepository` or `JpaRepository` for basic CRUD operations.
  - **Generics in Java**:
    - Understand how generics provide type safety and code reusability.
    - Implement generic services or repositories if applicable.
  - **Validation**:
    - Use validation annotations like `@NotNull`, `@Size`, `@Email`.
    - Handle validation exceptions using `@ControllerAdvice` and `@ExceptionHandler`.

- **Tasks**:
  - Define entities to manage orders and products in a store (e.g., `User`, `Product`).
  - Create a repository interface for the entity.
  - Implement service methods for CRUD operations using generics.
  - Add validation to entity fields.
  - Test validation by sending invalid data to the API.

- **Check-in Questions**:
  1. How does Spring Data JPA simplify database interactions?
  2. What are generics, and how do they enhance your code?
  3. How does Spring Boot handle input validation, and why is it important?

---

## 3. Serialization with Jackson, RESTful APIs, and Dockerization

- **Objective**: Implement RESTful APIs with proper serialization, and Dockerize the application.

- **Topics**:
  - **Serialization with Jackson**:
    - Understand how Jackson converts Java objects to JSON and vice versa.
    - Customize serialization using `@JsonProperty`, `@JsonIgnore`.
  - **RESTful API Development**:
    - Implement endpoints for CRUD operations.
    - Use appropriate HTTP status codes and methods (`GET`, `POST`, `PUT`, `DELETE`).
    - Handle exceptions and return meaningful error responses and status codes
  - **Dockerization**:
    - Write a `Dockerfile` for the Spring Boot application.
    - Build and run the Docker image.
    - Use Docker Compose to run the application with MySQL.

- **Tasks**:
  - Implement full CRUD REST APIs for your entity.
  - Customize JSON responses using Jackson annotations.
  - Create a `Dockerfile` and build the Docker image.
  - Write a `docker-compose.yml` file to run both the application and MySQL.
  - Test the application running in Docker containers.

- **Check-in Questions**:
  1. How does Jackson handle serialization and deserialization in Spring Boot?
  2. What are the benefits of using Docker for application deployment?
  3. Explain the key components of a `Dockerfile` and `docker-compose.yml`.
  4. When should status code `4xx` be returned ? How about `5xx` ?

---

## 4. Authentication and Authorization with JWT, and Advanced Dependency Injection

- **Objective**: Secure the application using JWT, implement role-based access, and explore advanced dependency injection concepts.

- **Topics**:
  - **Authentication with JWT**:
    - Implement user registration and login endpoints.
    - Hash passwords securely (e.g., using BCrypt).
    - Generate JWT tokens upon successful authentication.
  - **Authorization**:
    - Secure API endpoints using Spring Security.
    - Implement role-based access control.
  - **Advanced Dependency Injection**:
    - Understand the use of `@Qualifier` and `@Primary`.
    - Manage multiple implementations of an interface.
    - Use profiles (`@Profile`) for different configurations.

- **Tasks**:
  - Set up Spring Security in the project.
  - Implement JWT token generation and validation.
  - Secure API endpoints based on user roles.
  - Create multiple service implementations and inject them using qualifiers.
  - Switch configurations using Spring profiles.

- **Check-in Questions**:
  1. What is the difference between authentication and authorization?
  2. How do JWT tokens improve stateless authentication?
  3. How does Spring handle multiple beans of the same type with dependency injection?
  4. Explain your understanding on differences between hashing and encryption

---

## 5. Unit and Integration Testing with JUnit and Mockito, and Project Review

- **Objective**: Write unit and integration tests, ensure code quality, and finalize the project.

- **Topics**:
  - **Unit Testing with JUnit and Mockito**:
    - Write unit tests for service and controller layers.
    - Mock dependencies to isolate units of code.
    - Use assertions to validate the behavior.
  - **Integration Testing**:
    - Test the application flow from end to end.
    - Use `MockMvc` or `TestRestTemplate` to test API endpoints.
    - Test security configurations with authenticated and unauthenticated requests.
  - **Code Review and Best Practices**:
    - Refactor code for readability and maintainability.
    - Apply SOLID principles and design patterns.
    - Optimize Docker configurations for production readiness.

- **Tasks**:
  - Write unit tests covering key functionalities.
  - Implement integration tests for API endpoints, including security.
  - Review the entire codebase for potential improvements.
  - Build and run the final Docker images and ensure everything works together.
  - Document the API endpoints and how to run the project using swagger

- **Check-in Questions**:
  1. How do unit tests differ from integration tests, and why are both important?
  3. How can you ensure your Dockerized application is ready for production deployment?

