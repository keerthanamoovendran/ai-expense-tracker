AI Expense Tracker

A full-stack personal finance web application built with **Java Spring Boot** and **MySQL**, that lets users log and manage daily expenses — and uses **Google's Gemini AI API** to generate personalized spending advice based on real usage data.

---

Features

- Add, view, and delete expenses through a clean dashboard
- Categorize expenses (Food, Transport, Shopping, Bills, Entertainment, Health, Other)
- Auto-calculated running total of all spending
- AI-generated, personalized spending advice powered by Google Gemini
- REST API for all expense operations (CRUD)
- Custom-designed frontend (HTML, CSS, vanilla JavaScript — no framework)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java, Spring Boot, Spring Data JPA |
| Database | MySQL |
| Frontend | HTML, CSS, JavaScript (Fetch API) |
| Templating | Thymeleaf |
| AI Integration | Google Gemini API |
| Build Tool | Maven |

---

## Project Structure

```
expense-tracker/
│
├── src/main/java/com/expense/expense_tracker/
│   ├── controller/
│   │   ├── ExpenseController.java   - REST API for expense CRUD
│   │   ├── WebController.java       - Serves HTML pages
│   │   └── AIController.java        - REST endpoint for AI advice
│   │
│   ├── service/
│   │   ├── ExpenseService.java      - Business logic for expenses
│   │   └── AIService.java           - Builds prompts, calls Gemini API
│   │
│   ├── repository/
│   │   └── ExpenseRepository.java   - Spring Data JPA repository
│   │
│   ├── model/
│   │   └── Expense.java             - Entity mapped to the expenses table
│   │
│   └── ExpenseTrackerApplication.java
│
├── src/main/resources/
│   ├── templates/
│   │   ├── index.html       - Homepage
│   │   ├── dashboard.html   - Add / view / delete expenses
│   │   └── advice.html      - AI-generated spending advice
│   └── application.properties.example   - Config template (copy this, see below)
│
└── pom.xml
```

---

 How It Works

1. **Add an expense** — the dashboard form sends a POST request to `/api/expenses`, which goes through `ExpenseController` to `ExpenseService` to `ExpenseRepository`, and is saved in MySQL via Hibernate.
2. **View expenses** — the dashboard calls `GET /api/expenses` on page load and renders the results in a table.
3. **Get AI advice** — the `/advice` page calls `GET /api/advice`. `AIService` pulls all expenses from the database, builds a text prompt summarizing the spending, sends it to the Gemini API, and returns the AI's generated tips.

---

 Getting Started

 Prerequisites

- Java 17 or higher
- MySQL installed and running
- A free Gemini API key from Google AI Studio (aistudio.google.com/app/apikey)

### Setup

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/ai-expense-tracker.git
   cd ai-expense-tracker
   ```

2. Create the database
   ```sql
   CREATE DATABASE expense_tracker;
   ```

3. Configure your environment

   Copy the example config file and fill in your real values:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

   Then edit `application.properties` with your actual MySQL password and Gemini API key:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.thymeleaf.cache=false
   gemini.api.key=YOUR_GEMINI_API_KEY
   gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
   ```

4. Run the application
   ```bash
   mvnw.cmd spring-boot:run
   ```
   Use `./mvnw spring-boot:run` on Mac/Linux.

5. Open in your browser
   ```
   http://localhost:8080/
   ```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/expenses | Get all expenses |
| GET | /api/expenses/{id} | Get a single expense |
| POST | /api/expenses | Create a new expense |
| PUT | /api/expenses/{id} | Update an expense |
| DELETE | /api/expenses/{id} | Delete an expense |
| GET | /api/advice | Get AI-generated spending advice |

---

Screenshots

Add screenshots of the homepage, dashboard, and advice page here.



 Future Improvements

 Edit existing expenses through the dashboard UI
 User authentication (multi-user support)
 Input validation (e.g. prevent negative amounts)
 Spending charts and graphs by category
 Filter expenses by date range or category
 Automated tests for the service layer



## License

This project is open source and available for learning purposes.
