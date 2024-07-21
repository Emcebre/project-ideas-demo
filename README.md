# Project Ideas Demo

## Table of Contents
- [Project Description](#project-description)
- [Technology Choices](#technology-choices)
- [Selected Data Store](#selected-data-store)
- [Considerations When Building the API](#considerations-when-building-the-api)
- [API Security](#api-security)
- [API Documentation](#api-documentation)
- [Frontend Integration Instructions](#frontend-integration-instructions)
- [Running the Project](#running-the-project)

## Project Description
The project aims to create a REST API for listing and creating ideas. The API allows for the management of projects and ideas associated with these projects.

## Technology Choices
- **Spring Boot**: A popular framework for creating web applications in Java, offering auto-configuration and many built-in features.
- **MapStruct**: A library for object mapping, minimizing manual coding.

## Selected Data Store
- **H2 Database**: A lightweight, embedded database used during development and testing phases.
- **Hibernate**: An ORM framework that simplifies the mapping of objects to database tables.

## Considerations When Building the API
- **API Structure**: Endpoints are organized into logical groups, supporting CRUD operations.
- **Data Validation**: Use of validation annotations to ensure data integrity.
- **Exception Handling**: Global exception handling to provide consistent error messages.
- **Testing**: Unit and integration tests to ensure API correctness.

## API Security
- **Spring Security**: A comprehensive solution for securing applications.
- **OAuth2/JWT**: Standards for user authentication and authorization, providing scalability and security.

## API Documentation
The API documentation is available in the README.md file in the project repository.

## Frontend Integration Instructions
Hi [Frontend Engineer],

I'd like to introduce you to the API I've created for our project. Here are some details to help you with frontend integration:

### API Endpoints

1. **List of Ideas:**
    - **URL**: `GET /api/projects/ideas`
    - **Description**: Retrieves a list of all ideas.
    - **Sample Response**:
      ```json
      [
          {
              "id": 1,
              "title": "Idea1",
              "description": "Description for idea 1",
              "author": "Author1",
              "graphicDesignerName": "Designer 1",
              "imageUrl": "http://example.com/image1.jpg"
          },
          {
              "id": 2,
              "title": "Idea2",
              "description": "Description for idea 2",
              "author": "Author2",
              "graphicDesignerName": "Designer 2",
              "imageUrl": "http://example.com/image2.jpg"
          }
      ]
      ```

2. **Creating a New Idea:**
    - **URL**: `POST /api/projects/ideas`
    - **Description**: Creates a new idea.
    - **Request Body**:
      ```json
      {
          "title": "NewIdea",
          "description": "Description for new idea",
          "author": "Author",
          "graphicDesignerName": "Designer",
          "imageUrl": "http://example.com/image.jpg"
      }
      ```
    - **Sample Response**:
      ```json
      {
          "id": 3,
          "title": "NewIdea",
          "description": "Description for new idea",
          "author": "Author",
          "graphicDesignerName": "Designer",
          "likes": 0,
          "imageUrl": "http://example.com/image.jpg"
      }
      ```

If you have any questions or encounter any issues, please let me know.

Best regards,  
Kamil

## Running the Project
1. Clone the repository.
```bash
    ./git clone <repository>
```
2. Navigate to the project directory.
```bash
    ./cd project-ideas-demo
```
3. Build the project thanks to Maven:
   ```bash
   ./mvn clean install
   ```
4. Run the following command to start the application:
   ```bash
   ./mvnw spring-boot:run
   ```