# My Kakeibo

A personal finance tracker inspired by the Japanese practice of **Kakeibo (家計簿)**.  
The goal is not only to track income and expenses, but also to **reflect** on spending habits and increase financial awareness.

---

## Features
- Record **expenses** and **income**.
- Organize entries into **categories**.
- Write **monthly reflections** (thoughts and observations).
- Multi-user support with customizable preferences (currency, categories, etc.).
- API-first design for future frontend integration.

---

## Database Design (ER Diagram)

The app uses a relational database.  
Each user can create categories, log incomes and expenses, and write reflections at the end of each month.  

```mermaid
erDiagram
    USER ||--o{ CATEGORY : has
    USER ||--o{ EXPENSE : makes
    USER ||--o{ INCOME : earns
    USER ||--o{ REFLECTION : writes

    CATEGORY ||--o{ EXPENSE : classifies
    CATEGORY ||--o{ INCOME : classifies

    USER {
        uuid user_id PK
        string name
        string email
        string password_hash
        string password_salt
        string currency_preference
        date created_at
        date updated_at
        bool is_active
        string activation_token
        date activation_token_expiry
    }

    CATEGORY {
        int category_id PK
        int user_id FK
        string name
        string type "ENUM(income, expense)"
    }

    EXPENSE {
        int expense_id PK
        int user_id FK
        int category_id FK
        decimal amount
        date date
        string description
        text note
    }

    INCOME {
        int income_id PK
        int user_id FK
        int category_id FK
        decimal amount
        date date
        string description
        text note
    }

    REFLECTION {
        int reflection_id PK
        int user_id FK
        date month_year
        text content
        date created_at
    }
```
---

## System Design (UML Diagram)
```mermaid
classDiagram
    class User {
        +uuid id
        +string name
        +string email
        +string passwordHash
        +string currencyPreference
        +Date createdAt
        +Date updatedAd
        +boolean isActive
        +string activationToken
        +string activationTokenExpiry
        +registerUser()
        +sendAccountActivationMail()
        +activateUser()
        +login()
        +updateProfile()
        +setCurrencyPreference(currency)
    }

    class Category {
        +int id
        +string name
        +string type
        +createCategory(name, type)
        +updateCategory()
        +deleteCategory()
    }

    class Expense {
        +int id
        +decimal amount
        +Date date
        +string description
        +string note
        +addExpense(amount, date, category)
        +updateExpense()
        +deleteExpense()
    }

    class Income {
        +int id
        +decimal amount
        +Date date
        +string description
        +string note
        +addIncome(amount, date, category)
        +updateIncome()
        +deleteIncome()
    }

    class Reflection {
        +int id
        +Date monthYear
        +string content
        +Date createdAt
        +writeReflection(monthYear, content)
        +updateReflection()
        +getReflectionByMonth(monthYear)
    }

    User "1" --> "*" Category : owns
    User "1" --> "*" Expense : makes
    User "1" --> "*" Income : earns
    User "1" --> "*" Reflection : writes

    Category "1" --> "*" Expense : classifies
    Category "1" --> "*" Income : classifies
```
