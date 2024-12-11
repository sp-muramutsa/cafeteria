# Rowdy's Dining App 

## Overview

This repository contains both the backend and frontend for the Rowdy's Dining App, which is a platform for managing dining services. The backend is built using **Spring Boot** (Java), and the database is **MySQL**. The frontend for this app is developed as a Single Page Application (SPA) using **React** and **TypeScript**.

## Technologies Used

- **Backend**: 
  - **Java** with **Spring Boot**
  - **MySQL** for the database
  - **JPA/Hibernate** for object-relational mapping (ORM)
  - **Spring Security** for user authentication and authorization
  - **Spring Data JPA** for database interactions
  - **Spring Web** for RESTful APIs
  - **JSON Web Token** for JWT Authentication

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
   git clone https://github.com/sp-muramutsa/cafeteria.git
   cd caferia
   cd server
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
     spring.datasource.url=jdbc:mysql://localhost:3306/<your_database_name>
     spring.datasource.username=<your_username>
     spring.datasource.password=<your_password>
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     ```

4. **Run the backend**:
   - To run the backend server locally:
   
     ```bash
     mvn spring-boot:run
     ```

   - The backend API will be available at `http://localhost:8080`.

### Frontend

1. **Navigate to the frontend sub-directory**:
   ```bash
   cd web
   cd client
   ```

2. **Install dependencies**:
   - Ensure **Node.js** and **npm** are installed.
   - Install the frontend dependencies:

     ```bash
     npm install
     ```

3. **Run the frontend**:
   - To start the development server for the frontend:

     ```bash
     npm start
     ```

   - The frontend will be available at `http://localhost:3000`.

## Features

- **User Authentication**: Users can register, confirm their email, log in, and access private routes using JWT tokens for authentication.
- **Admin User Management**: RESTful API endpoints that support admin CRUD operations in user management.
- **Admin Ingredient Management**: RESTful API endpoints that support admin CRUD operations in ingredient management.
- **Admin Food Items Management**: RESTful API endpoints that support admin CRUD operations in food items management.
-**Customer CRUD Operations**: RESTful API endpoints that support customer CRUD operations in food order management.

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

For further details or any issues, feel free to raise a GitHub issue, and we’ll be happy to assist.
