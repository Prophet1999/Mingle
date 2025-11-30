# Mingle - Dating Application

Mingle is a modern dating application designed to connect people. It features real-time chat, geolocation-based matching, and secure authentication.

## Features

-   **User Profiles**: Create and manage detailed user profiles.
-   **Matching**: Geolocation-based matching algorithm to find people nearby.
-   **Real-time Chat**: Instant messaging with matched users.
-   **Authentication**: Secure login with Email/Password, Google, and Facebook (OAuth2).
-   **File Uploads**: Upload profile photos.

## Tech Stack

### Backend
-   **Java 17**
-   **Spring Boot 3.2.3**
-   **Spring Data JPA** (PostgreSQL)
-   **Spring Security** (OAuth2, JWT)
-   **Spring WebSocket** (Real-time chat)
-   **PostgreSQL** (with PostGIS for geolocation)

### Frontend
-   **Angular 17**
-   **TypeScript**
-   **Tailwind CSS 3.3**
-   **RxJS**

### Infrastructure
-   **Docker** & **Docker Compose** (for database)

## Prerequisites

Ensure you have the following installed:
-   [Java 17 SDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
-   [Node.js](https://nodejs.org/) (v18+ recommended)
-   [Docker Desktop](https://www.docker.com/products/docker-desktop/)

## Configuration

The backend requires a PostgreSQL database. The default configuration in `application.yml` expects:
-   **URL**: `jdbc:postgresql://localhost:5432/mingle`
-   **Username**: `mingle`
-   **Password**: `mingle_password`

### Environment Variables
For OAuth2 authentication to work, you need to set the following environment variables or update `application.yml`:

-   `GOOGLE_CLIENT_ID`
-   `GOOGLE_CLIENT_SECRET`
-   `FACEBOOK_CLIENT_ID`
-   `FACEBOOK_CLIENT_SECRET`

## Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd Mingle
```

### 2. Start the Database
Use Docker Compose to start the PostgreSQL database with PostGIS.
```bash
docker-compose up -d
```

### 3. Backend Setup
Navigate to the backend directory and run the application.
```bash
cd mingle-backend
./mvnw spring-boot:run
```
The backend will start on `http://localhost:8080`.

### 4. Frontend Setup
Navigate to the frontend directory, install dependencies, and start the development server.
```bash
cd ../mingle-frontend
npm install
ng serve
```
The frontend will be available at `http://localhost:4200`.

## API Documentation
The backend exposes RESTful APIs for user management, matching, and chat.
(Add Swagger/OpenAPI link if available, e.g., `http://localhost:8080/swagger-ui.html`)

## License
[MIT](LICENSE)
