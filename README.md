# bookticketapp
# Online Movie Ticket Booking Application

## Overview
The Online Movie Ticket Booking Application is a robust and secure platform designed for users to book movie tickets and for admins to manage movie-related data. The application ensures secure login with user and admin roles, allows image uploads for movie updates, and generates PDF tickets after booking. It is developed using a modern tech stack with a focus on MVC architecture.

---

## Features
- **Admin and User Login**:
  - Separate login roles for Admin and Users with security checks.
  - Robust authentication and authorization.

- **Movie Ticket Booking**:
  - Users can book tickets for movies.
  - Generates a PDF ticket after successful booking.

- **Admin Functionalities**:
  - Admins can perform CRUD (Create, Read, Update, Delete) operations on all tables.
  - Supports uploading images for movies using MultipartFile.

- **User Functionalities**:
  - Users can read data from all tables.
  - Users can update ticket details in the ticket table.
  - Browse available movies and book tickets.
  - View booked tickets and download PDF copies.

- **Database Design**:
  - Optimized relational database structure for handling users, movies, theatres, shows, screens, and seats.
  - Supports seamless integration with Hibernate ORM.

---

## Technology Stack
- **Backend**: Spring Boot
- **Frontend**: Thymeleaf
- **Database**: MySQL
- **ORM**: Hibernate (DAO Architecture)
- **Architecture**: Model-View-Controller (MVC)
- **Others**: MultipartFile for image upload, PDF generation for ticket booking

---

## Database Design
The database is designed to efficiently manage users, roles, movies, theatres, screens, shows, seats, and ticket bookings. Below is the ER diagram of the database structure:

<img width="982" alt="Screenshot 2025-01-17 at 10 39 18 PM" src="https://github.com/user-attachments/assets/b35f0e97-1116-4f05-852a-cabbfd85654f" />


### Key Tables
1. **Users**:
   - Stores user and admin details.
   - Includes role-based authorization.

2. **Roles**:
   - Differentiates between Admin and User roles.

3. **Movies**:
   - Stores movie details like title, genre, director, actor, and imagePath.

4. **Theatres**:
   - Information about theatres and their locations.

5. **Screens**:
   - Tracks screens available in theatres.

6. **Shows**:
   - Stores show information such as show name and timing.

7. **Seats**:
   - Manages seat availability and booking status.

8. **Movie Details and Theatre Mapping**:
   - Links movie details to specific theatres.

---

## Setup and Installation

### Prerequisites
- Java 8 or higher
- Maven
- MySQL
- Spring Boot CLI

### Steps
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd online-movie-ticket-booking
   ```

2. **Configure Database**:
   - Create a MySQL database.
   - Update `application.properties` with database details:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application**:
   - Admin Portal: `http://localhost:8080/adminPage`
   - User Portal: `http://localhost:8080/u/homepage`

---

## How to Use

### Admin Role
- Login with admin credentials.
- Perform CRUD operations on movies, theatres, screens, shows, seats, and other tables.
- Upload movie images using the admin panel.

### User Role
- Login with user credentials.
- Browse available movies and book tickets.
- Read data from all tables.
- Update ticket details in the ticket table.
- Download PDF tickets after booking.

---

## Future Enhancements
- Add payment gateway integration.
- Implement notifications for booking confirmations.
- Introduce seat selection with real-time updates.
- Multi-language support for broader audience reach.

---

## Contributors
- Rohith Varma (datla.b@northeastern.edu)


