# Dining App Fullstack Backend Repository

## Overview

This repository contains the backend for the Dining App, which is a web-based platform for managing dining services. The backend is built using **Spring Boot** (Java), and the database is **MySQL**. The frontend for this app is developed as a Single Page Application (SPA) using **React** and **TypeScript**.

## Technologies Used

- **Backend**: 
  - **Java** with **Spring Boot**
  - **MySQL** for the database
  - **JPA/Hibernate** for object-relational mapping (ORM)
  - **Spring Security** for user authentication and authorization
  - **Spring Data JPA** for database interactions
  - **Spring Web** for RESTful APIs

- **Frontend**:
  - **React** SPA (Single Page Application)
  - **TypeScript** for type safety
  - **Axios** for HTTP requests to interact with the backend
  - **Tailwind CSS** for styling
  - **React Router** for navigation

## Setup Instructions

### Backend

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/dining-app-backend.git
   cd dining-app-backend
   ```

2. **Install dependencies**:
   - Ensure you have **Java** (JDK 11 or higher) installed.
   - Install **Maven** for building the project.

   ```bash
   mvn install
   ```

3. **Configure MySQL Database**:
   - Set up a MySQL database for the project.
   - Update the database connection settings in `src/main/resources/application.properties`:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/dining_db
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     ```

4. **Run the backend**:
   - To run the backend server locally:
   
     ```bash
     mvn spring-boot:run
     ```

   - The backend API will be available at `http://localhost:8080`.

### Frontend

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/dining-app-frontend.git
   cd dining-app-frontend
   ```

2. **Install dependencies**:
   - Ensure **Node.js** and **npm** are installed.
   - Install the frontend dependencies:

     ```bash
     npm install
     ```

3. **Configure API endpoint**:
   - In the `src/api` directory, update the API base URL to match the backend URL in your project:

     ```typescript
     const apiBaseUrl = "http://localhost:8080/api";  // Update if needed
     ```

4. **Run the frontend**:
   - To start the development server for the frontend:

     ```bash
     npm start
     ```

   - The frontend will be available at `http://localhost:3000`.

## Features

- **User Authentication**: Users can register, log in, and manage their profiles using JWT tokens for authentication.

## Contributing

Feel free to fork the repository, make changes, and create pull requests. For major changes, please open an issue first to discuss the changes.

1. Fork the repository.
2. Create a new branch for your changes (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m 'Add feature'`).
4. Push to your branch (`git push origin feature-name`).
5. Create a pull request.

## License

This project is licensed under the MIT License.

---

For further details or any issues, feel free to raise a GitHub issue, and I’ll be happy to assist.
