# Inventory Management System - Java 17 Spring Boot Migration

## Overview
This project has been successfully migrated from a Java 16 NetBeans Swing desktop application to a Java 17 Spring Boot web application.

## Migration Summary

### Before (Original)
- **Platform**: Java 16 NetBeans Swing Desktop Application
- **Build System**: NetBeans/Ant
- **Database Access**: JDBC with custom DAO pattern
- **UI**: Java Swing components
- **Authentication**: Custom desktop login
- **Architecture**: DAO/DTO pattern with ConnectionFactory

### After (Migrated)
- **Platform**: Java 17 Spring Boot Web Application
- **Build System**: Maven
- **Database Access**: Spring Data JPA with repositories
- **UI**: Thymeleaf templates with Bootstrap CSS
- **Authentication**: Spring Security with web-based login
- **Architecture**: Spring MVC with Service layer

## Key Features Preserved
- ✅ Product Management (CRUD operations)
- ✅ Customer Management (CRUD operations)
- ✅ Supplier Management (CRUD operations)
- ✅ User Management (Admin only)
- ✅ Sales Transaction Processing
- ✅ Purchase Transaction Processing
- ✅ Current Stock Management
- ✅ User Activity Logs
- ✅ Search functionality across all entities
- ✅ Role-based access control (Administrator/Employee)

## Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA** (for database operations)
- **Spring Security** (for authentication and authorization)
- **MySQL** (database - same schema as original)
- **Maven** (build tool)

### Frontend
- **Thymeleaf** (templating engine)
- **Bootstrap 5.1.3** (CSS framework)
- **Font Awesome 6.0.0** (icons)
- **Custom CSS and JavaScript** (enhanced UI/UX)

## Database Configuration
The application uses the same MySQL database schema as the original application. Update the database connection settings in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory
spring.datasource.username=root
spring.datasource.password=root
```

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Existing inventory database (use SQL/InventoryDB.sql)

### Steps
1. Clone the repository
2. Navigate to the project directory
3. Update database credentials in `application.properties`
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application at: http://localhost:8080

### Default Login Credentials
- **Username**: root
- **Password**: root
- **User Type**: Administrator

## Project Structure
```
src/
├── main/
│   ├── java/com/inventory/
│   │   ├── entity/          # JPA entities (Customer, Product, User, etc.)
│   │   ├── repository/      # Spring Data JPA repositories
│   │   ├── service/         # Business logic services
│   │   ├── controller/      # Spring MVC controllers
│   │   ├── config/          # Spring Security configuration
│   │   └── InventoryManagementApplication.java
│   ├── resources/
│   │   ├── templates/       # Thymeleaf HTML templates
│   │   ├── static/          # CSS, JS, and other static files
│   │   └── application.properties
│   └── test/               # Unit tests (to be added)
```

## Key Improvements
1. **Modern Web Interface**: Responsive Bootstrap-based UI
2. **Enhanced Security**: Spring Security with BCrypt password encoding
3. **Better Architecture**: Clean separation of concerns with Spring layers
4. **Improved Performance**: JPA with connection pooling
5. **Maintainability**: Standard Spring Boot project structure
6. **Scalability**: Web-based architecture supports multiple concurrent users

## API Endpoints
The application provides web-based CRUD operations for:
- `/customers` - Customer management
- `/products` - Product management
- `/suppliers` - Supplier management
- `/sales` - Sales transactions
- `/purchases` - Purchase transactions
- `/users` - User management (Admin only)
- `/users/logs` - User activity logs (Admin only)

## Migration Notes
- All original business logic has been preserved
- Database schema remains unchanged
- User roles and permissions are maintained
- Search functionality is available on all list pages
- Form validation matches original application requirements

## Future Enhancements
- REST API endpoints for mobile/external integrations
- Advanced reporting and analytics
- Real-time stock alerts
- Barcode scanning support
- Multi-location inventory management
