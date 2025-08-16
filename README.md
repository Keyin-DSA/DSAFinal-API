# Binary Search Tree API

A Spring Boot backend that builds, serializes, and stores binary search trees.  
It exposes endpoints for creating new trees and retrieving previously saved trees.

---

## Prerequisites

- Java 21  
- Maven 3.9+  
- MySQL (local or remote)  

---

## Setup

1. **Clone the repository**  
   ```bash
   git clone https://github.com/Keyin-DSA/BinarySearchTree-API.git
   cd BinarySearchTree-API
   ```

2. **Configure the database**  
   Update your `application.properties` (or `application.yml`) with your own database connection:

   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/bst_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

   ⚠️ **Note:** Be sure to create the database manually in MySQL (`bst_db` in the example above), or change the name to match your setup.

3. **Build and run the app**  
   ```bash
   ./mvnw clean package
   java -jar target/binarysearchtree-0.0.1-SNAPSHOT.jar
   ```

4. **Access the API**  
   The backend will be available at [http://localhost:8080](http://localhost:8080).  

---

## Endpoints

- **POST** `/process-numbers` → Create a new binary search tree from an integer list  
- **GET** `/previous-trees` → Retrieve all previously saved trees  

---

## Tech Stack

- **Spring Boot** (REST API)  
- **Spring Data JPA** (repository layer)  
- **MySQL** (database)  
- **Jackson** (JSON serialization)  

---

## License

MIT License.
