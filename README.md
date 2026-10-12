# 🍕 FoodieBite — Online Food Ordering System
### Built with Java 21, Spring Boot 3, Maven, Thymeleaf, Spring Security, and MySQL

A complete, enterprise-grade, beginner-friendly **Online Food Ordering website** built with a **Java & Spring Boot** backend, **Spring Security** (form-based authentication), **Spring Data JPA & Hibernate**, a **MySQL** relational database, and responsive **Thymeleaf + HTML5 / CSS3 / JavaScript** templates.

---

## 🛠️ Technology Stack

- **Backend Framework**: Java 21 LTS + Spring Boot 3.3.4 (Spring Web, Spring Security, Spring Data JPA, Hibernate ORM)
- **Template Engine**: Thymeleaf (`src/main/resources/templates/`)
- **Database**: MySQL Server (Database: `foodiedb` on port 3306)
- **Frontend**: HTML5, Modern CSS3 (Responsive Grid & Glassmorphism), Vanilla JavaScript (ES6+), FontAwesome Icons
- **Build Tool**: Maven Wrapper (`mvnw.cmd` / `mvnw` included)

---

## 🚀 Quick Start — How to Run Locally

### 1. Ensure MySQL is Running
Make sure your MySQL database service is started (e.g. via **XAMPP Control Panel**, **WAMP**, or **MySQL Workbench**):
- Default port: `3306`
- Default user: `root`
- Default password: *(blank by default in XAMPP/WAMP)*

> [!TIP]
> If your MySQL root user has a password set, open `src/main/resources/application.properties` and update:
> ```properties
> spring.datasource.password=your_mysql_password
> ```

Spring Boot will automatically create the `foodiedb` database and its tables on startup via `spring.jpa.hibernate.ddl-auto=update` and populate sample data via `DataInitializer.java`. You can also import `foodiedb.sql` in phpMyAdmin or MySQL Workbench if you prefer manual SQL setup.

---

### 2. Start the Spring Boot Application
Open PowerShell or Command Prompt in this folder:
```powershell
cd c:\Users\sindh\OneDrive\Documents\Desktop\onlinefoodordering
```

Start the application using the Maven Wrapper:
```powershell
.\mvnw.cmd spring-boot:run
```

*(Or run the packaged JAR directly: `java -jar target/foodiebite-1.0.0.jar`)*

---

### 3. Open in Your Web Browser
The application runs on port **8081** (to avoid port 8080 conflicts with Oracle XML DB / TNSLSNR):
- 🌐 **Storefront & Menu**: [http://localhost:8081](http://localhost:8081)
- 🔐 **Customer & Admin Login**: [http://localhost:8081/login](http://localhost:8081/login)
- ⚙️ **Admin Dashboard**: [http://localhost:8081/admin](http://localhost:8081/admin)

---

## 🔒 Security & Authentication Architecture

1. **No HTTP Basic Popup**:
   - `httpBasic` is explicitly disabled in `SecurityConfig.java`.
   - Browser popup is eliminated.
2. **Public Storefront Access**:
   - Visitors can browse the home page, explore food dishes, search/filter, and add items to cart without being asked to log in.
3. **Form-Based Authentication**:
   - Clean, custom HTML login page at `/login` with 1-click demo buttons.
   - Unauthorized attempts to access `/admin` cleanly redirect to `/login` via `LoginUrlAuthenticationEntryPoint("/login")`.
4. **Role-Based Access Control**:
   - Customers (`ROLE_CUSTOMER`): Storefront, Cart, Checkout, Order Tracking.
   - Admins (`ROLE_ADMIN`): Full access to the Admin Dashboard (`/admin`), Revenue Stats, Order Status updates, and Menu Catalog CRUD.

---

## 🔑 Pre-Configured Demo Accounts

| Role | Email | Password | Access & Features |
|---|---|---|---|
| **Demo Customer** | `customer@foodie.com` | `user123` | Browse menu, search/filter, dietary badges, add to cart, coupons (`FOODIE20`), checkout, track orders |
| **Store Admin** | `admin@foodie.com` | `admin123` | Revenue & order stats, real-time order status updates, add/edit/delete dishes in MySQL |
