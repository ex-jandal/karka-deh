# Karka Deh

Karka Deh is a full-stack web application that allows users to create, read, and comment on posts. It features a modern front-end built with Vue.js and a robust back-end powered by Java and Spring Boot.

## Features

*   User authentication (signup and login).
*   Create, read, update, and delete posts.
*   Comment on posts.
*   Search for posts.

## Technologies Used

*   **Front-end:**
    *   Vue.js
    *   TypeScript
    *   Vite
*   **Back-end:**
    *   Java
    *   Spring Boot
    *   Gradle
*   **Database:**
    *   PostgreSQL
    *   Docker Compose

## Getting Started

### Prerequisites

*   Node.js and pnpm
*   Java Development Kit (JDK)
*   Gradle
*   Docker

### 1. Database

Navigate to the `database-compose` directory and run the following command to start the PostgreSQL database:

```bash
docker-compose up -d
```

### 2. Back-end

Navigate to the `back-end` directory and run the following command to start the back-end server:

```bash
./gradlew bootRun
```

The back-end server will be running on `http://localhost:8080`.

### 3. Front-end

Navigate to the `front-end` directory and run the following commands to start the front-end development server:

```bash
pnpm install
pnpm dev
```

The front-end development server will be running on `http://localhost:5173`.

## API Documentation

The Postman collection and environment are available in the `back-end/postman` directory. You can import these into Postman to test the API.

## Folder Structure

```
.
├── back-end
├── database-compose
└── front-end
```

*   `back-end`: Contains the Java/Spring Boot application.
*   `database-compose`: Contains the Docker Compose configuration for the PostgreSQL database.
*   `front-end`: Contains the Vue.js front-end application.

## License

This project is open-source and available under the [GPL-3.0 license](LICENSE).
