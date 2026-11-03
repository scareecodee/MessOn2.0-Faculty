# 🎓 MessOn Faculty

### Multi-Tenant Mess Administration & Management Platform

**MessOn Faculty** is a faculty-facing application built as part of the MessOn Digital Mess Management Ecosystem. It provides a centralized platform for managing multiple mess establishments, students, mess managers, billing operations, holidays, and notifications.

Developed using **Kotlin and Compose Multiplatform**, the application follows modern software engineering principles, including MVVM, Clean Architecture, dependency injection, and reactive state management.

MessOn Faculty is designed around a multi-tenant architecture, allowing multiple mess establishments to operate within a shared ecosystem while maintaining their respective operational contexts.

---

## 📌 Table of Contents

- [About the Project](#-about-the-project)
- [Key Features](#-key-features)
- [Multi-Tenant Architecture](#-multi-tenant-architecture)
- [Application Architecture](#-application-architecture)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Core Modules](#-core-modules)
- [Notification System](#-notification-system)
- [Backend Integration](#-backend-integration)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Development Guidelines](#-development-guidelines)
- [Future Enhancements](#-future-enhancements)
- [MessOn Ecosystem](#-messon-ecosystem)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🚀 About the Project

Managing multiple mess establishments, student records, mess managers, billing, and daily operations manually can become increasingly complex.

MessOn Faculty aims to simplify these operations through a unified digital administration platform.

Instead of maintaining separate systems for every mess, the platform provides centralized management capabilities through a shared backend and a dedicated faculty application.

### Project Objectives

- Centralize the management of multiple mess establishments.
- Simplify student administration.
- Manage mess managers and their responsibilities.
- Digitize mess holiday management.
- Simplify mess billing operations.
- Enable efficient bulk student data import.
- Facilitate communication through push notifications.
- Provide a scalable and maintainable architecture.

---

## ✨ Key Features

### 1. Multi-Tenant Mess Management

The application supports managing multiple mess establishments through a unified administration platform.

Key capabilities:

- Manage multiple mess establishments.
- Organize operations according to individual messes.
- Manage mess-specific student records.
- Associate mess managers with their respective messes.
- Maintain centralized administration.
- Support expansion as additional mess establishments join the ecosystem.

### 2. Student Management

Provides faculty members with tools to manage student information efficiently.

Key capabilities:

- View student records.
- Browse student listings.
- Select individual or multiple students.
- Perform student deletion operations.
- Manage student information through backend APIs.
- Organize student data within the mess management ecosystem.

### 3. Student Pagination

Efficiently handles large student datasets through paginated data retrieval.

Implementation details:

- Page-based student retrieval.
- Default page size of 20 records.
- Controlled student selection.
- Efficient handling of large student lists.
- Reactive UI updates through StateFlow.

Pagination reduces unnecessary data loading and helps maintain a responsive user experience.

### 4. CSV Student Import

Supports bulk student data import through CSV files.

Key capabilities:

- Select CSV files.
- Read and process student records.
- Import multiple student records.
- Integrate imported data with backend operations.

This simplifies student onboarding and reduces repetitive manual data entry.

### 5. Mess Manager Management

Provides centralized management of mess managers across multiple mess establishments.

Key capabilities:

- Manage mess manager records.
- Add and manage mess managers.
- Associate managers with their respective mess establishments.
- Organize administrative responsibilities.
- Support management across multiple mess establishments.

### 6. Mess Holiday Management

Provides faculty members with digital tools for managing mess holidays.

Key capabilities:

- Manage mess holiday schedules.
- Maintain holiday information.
- Communicate holiday announcements.
- Help students stay informed about mess service availability.

### 7. Mess Billing Management

Provides a dedicated module for managing mess-related billing operations.

Key capabilities:

- Access mess billing information.
- Manage billing-related records.
- Support centralized billing management.
- Simplify administrative handling of mess billing operations.

### 8. Mess Complaint Management

Provides faculty members with a centralized interface to manage and monitor complaints submitted by students across different mess establishments.

Key capabilities:
- View student-submitted mess complaints.
- Manage complaints associated with different mess establishments.
- Monitor complaint status and progress.
- Track pending, in-progress, and resolved complaints.
- Support complaint resolution and administrative follow-up.
- Maintain better communication between students, faculty, and mess management.

The complaint management module helps improve transparency, accountability, and the overall student experience.


### 9. Push Notification Management

Enables faculty members to communicate important announcements through push notifications.

Faculty members can compose notifications using:

- Notification title.
- Notification message.

Example:

**Title:** Menu Update

**Message:** Paneer for dinner tonight!

Notifications are integrated with Firebase Cloud Messaging (FCM).

### 9. Device Registration Management

Manages device registration operations required for push notification delivery.

Key components:

- Device registration.
- Device unregistration.
- Device repository abstraction.
- Push notification manager.
- Dedicated registration and unregistration use cases.

The backend maintains registered device tokens to facilitate notification delivery.


---

## 🏢 Multi-Tenant Architecture

Multi-tenancy is one of the central architectural concepts behind the MessOn ecosystem.

The platform is designed to support multiple mess establishments using a shared application and backend infrastructure.

Each mess operates within its own organizational context, with mess-specific management and operational data.

### High-Level Architecture

```text
                   MESSOn PLATFORM
                          |
                  CENTRAL BACKEND
                          |
                  FACULTY ADMIN
                          |
          +---------------+---------------+
          |               |               |
        MESS A          MESS B          MESS C
          |               |               |
     MESS MANAGER    MESS MANAGER    MESS MANAGER
          |               |               |
     +----+----+     +----+----+     +----+----+
     |         |     |         |     |         |
  Students  Billing Students  Billing Students Billing
     |         |     |         |     |         |
  Holidays  Menu   Holidays  Menu   Holidays  Menu
```

### Benefits of Multi-Tenancy

- **Centralized Infrastructure:** Multiple mess establishments use a common backend infrastructure.
- **Scalability:** New mess establishments can be incorporated into the ecosystem.
- **Organized Management:** Each mess has its own operational context.
- **Reduced Duplication:** Shared infrastructure avoids maintaining completely separate systems.
- **Maintainability:** Centralized application and backend development simplify updates.

**Data Isolation:** Tenant-specific authorization and data isolation should be enforced at the backend level to prevent unauthorized cross-mess access.

---

## 🏗️ Application Architecture

MessOn Faculty follows **Clean Architecture combined with the MVVM (Model-View-ViewModel) architectural pattern**.

The architecture separates presentation, business logic, and data operations to improve maintainability, testability, and scalability.

### Architecture Overview

```text
                 PRESENTATION LAYER
                         |
             +-----------+-----------+
             |                       |
          Compose UI             ViewModels
             |                       |
             +-----------+-----------+
                         |
                    DOMAIN LAYER
                         |
             +-----------+-----------+
             |                       |
          Use Cases              Models
             |                       |
             +-----------+-----------+
                         |
                     DATA LAYER
                         |
             +-----------+-----------+
             |                       |
        Repositories            Data Sources
             |                       |
             +-----------+-----------+
                         |
                   KTOR CLIENT
                         |
                    REST APIs
                         |
                  FASTAPI BACKEND
                         |
                    POSTGRESQL
```

### Presentation Layer

Responsible for rendering the user interface and handling user interactions.

Responsibilities:

- Compose UI screens.
- ViewModel-based state management.
- Handling user interactions.
- Observing reactive UI states.
- Managing loading, success, and error states.

### Domain Layer

Contains the core business logic.

Responsibilities:

- Use cases.
- Domain models.
- Repository interfaces.
- Business rules.

### Data Layer

Responsible for retrieving and managing application data.

Responsibilities:

- Repository implementations.
- API communication.
- Data models and mapping.
- Device registration operations.
- Backend integration.

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Compose Multiplatform | Declarative UI development |
| Material 3 | UI components and design system |
| MVVM | Presentation architecture |
| Clean Architecture | Separation of concerns |
| Kotlin Coroutines | Asynchronous operations |
| Kotlin Flow / StateFlow | Reactive state management |
| Ktor Client | HTTP networking |
| Koin | Dependency injection |
| Firebase Cloud Messaging | Push notifications |
| FileKit | Cross-platform file handling |
| Kermit | Logging and debugging |
| Gradle | Build automation |
| FastAPI | Backend framework |
| PostgreSQL | Relational database |

---

## 📂 Project Structure

The application follows a layered package organization.

```text
com.messon.faculty.app/
│
├── core/
│   ├── PushNotificationManager
│   └── ...
│
├── data/
│   ├── repository/
│   ├── remote/
│   ├── dto/
│   └── ...
│
├── domain/
│   ├── dataModel/
│   ├── repository/
│   ├── usecase/
│   └── ...
│
├── presentation/
│   ├── screen/
│   ├── components/
│   ├── viewmodel/
│   └── ...
│
├── di/
│   └── ...
│
└── ...
```

*Note: This is a representative structure. Refer to the repository for the exact package and directory organization.*

---

## 🧩 Core Architectural Concepts

### MVVM

The application uses Model-View-ViewModel to separate UI rendering from application logic.

- **Model:** Represents application and domain data.
- **View:** Displays UI using Compose.
- **ViewModel:** Handles user interactions and exposes observable UI state.

### Clean Architecture

Business logic is separated from external frameworks and implementation details.

Benefits:

- Improved maintainability.
- Independent business logic.
- Easier testing.
- Better separation of responsibilities.
- Easier integration of new features.

### Dependency Injection with Koin

Koin manages dependencies across different application layers.

It provides dependencies such as:

- ViewModels.
- Use cases.
- Repository implementations.
- Networking components.
- Notification services.

### Reactive State Management

StateFlow exposes observable state from ViewModels.

Compose UI observes these states and automatically updates when the underlying data changes.

---

## 🔔 Notification System

MessOn Faculty integrates Firebase Cloud Messaging with the central backend to facilitate communication across the MessOn ecosystem.

### Notification Flow

```text
       FACULTY APPLICATION
                |
        Notification UI
                |
             ViewModel
                |
            Use Case
                |
            Repository
                |
           Ktor Client
                |
         FastAPI Backend
                |
       Firebase Admin SDK
                |
     Firebase Cloud Messaging
                |
          +-----+-----+
          |           |
       FCM Topic   Device Tokens
          |           |
          +-----+-----+
                |
         Registered Devices
```

### Device Registration

The application uses dedicated abstractions for managing device registration.

Important components:

- `PushNotificationManager`
- `DeviceRepository`
- `RegisterDeviceUseCase`
- `UnregisterDeviceUseCase`

This approach keeps device-level operations separate from presentation logic.

### Example Notification Payload

```json
{
  "title": "Menu Update",
  "message": "Paneer for dinner tonight!",
  "topic": "mess_updates"
}
```

The backend processes the notification request and coordinates delivery through Firebase Cloud Messaging.

---

## 🔗 Backend Integration

MessOn Faculty communicates with the centralized MessOn backend.

### Backend Technology

- Python
- FastAPI
- PostgreSQL
- Firebase Cloud Messaging

### Backend Responsibilities

- Student record management.
- Student data import operations.
- Mess manager management.
- Mess-related administrative operations.
- Holiday management.
- Billing operations.
- Device token registration and unregistration.
- Notification delivery.
- Tenant-specific data management.

The backend acts as the central communication and data management layer for all MessOn applications.

---

## ⚙️ Getting Started

Follow these instructions to set up the project locally.

### Prerequisites

Make sure you have:

- Android Studio.
- JDK compatible with the project's Gradle configuration.
- Android SDK.
- Git.
- Kotlin and Compose Multiplatform-compatible Gradle environment.

### 1. Clone the Repository

```bash
git clone <YOUR_FACULTY_REPOSITORY_URL>
```

### 2. Navigate to the Project

```bash
cd MessOn-Faculty
```

### 3. Open the Project

Open the project directory in Android Studio.

Allow Gradle to synchronize and download the required dependencies.

### 4. Configure Backend

Configure the backend base URL in the appropriate networking configuration file.

Example:

```kotlin
const val BASE_URL = "https://your-backend-url.com"
```

Replace the placeholder with your actual backend URL.

### 5. Configure Firebase

To enable push notifications:

1. Configure the Firebase project associated with MessOn.
2. Add the required Firebase configuration files for supported targets.
3. Enable Firebase Cloud Messaging.
4. Configure Firebase Admin SDK credentials securely on the backend.
5. Verify device registration and notification delivery.

Never commit private service-account credentials to GitHub.

### 6. Build the Application

```bash
./gradlew build
```

To install the Android application:

```bash
./gradlew installDebug
```

---

## 🔐 Configuration and Security

Before running the application, ensure the following configurations are available:

| Configuration | Description |
|---|---|
| Backend URL | Base URL of the MessOn API |
| Firebase Configuration | Firebase project configuration |
| FCM Integration | Push notification setup |
| API Environment | Development or production environment |
| Tenant Context | Appropriate mess/tenant association |

### Security Recommendations

- Never hardcode sensitive credentials.
- Keep Firebase service-account credentials on the backend.
- Validate API responses.
- Use secure communication over HTTPS.
- Enforce authentication and authorization.
- Validate tenant access on every relevant backend operation.
- Prevent cross-tenant data access.
- Avoid committing secrets to version control.

---

## 📐 Development Guidelines

The project follows these development principles:

1. Maintain separation between presentation, domain, and data layers.
2. Keep business logic inside the domain layer.
3. Avoid placing networking logic directly inside Compose screens.
4. Use ViewModels to manage UI state.
5. Use Coroutines for asynchronous operations.
6. Use dependency injection instead of manually creating dependencies.
7. Keep reusable UI components separate from individual screens.
8. Handle loading, success, and failure states consistently.
9. Follow consistent Kotlin coding conventions.
10. Ensure tenant-specific operations respect backend authorization rules.

---

## 🚧 Future Enhancements

Potential areas for further development include:

- Advanced student analytics and reporting.
- More comprehensive student filtering and search.
- Enhanced notification targeting.
- Notification history and delivery tracking.
- Improved CSV validation and import reporting.
- Detailed faculty dashboards.
- Advanced billing reports.
- Audit logs for administrative operations.
- Automated testing and CI/CD integration.

---

## 🌐 MessOn Ecosystem

MessOn Faculty is one of three interconnected applications.

| Application | Description |
|---|---|
| **MessOn Student** | Student-facing application for meal management, coupons, complaints, and other mess services. |
| **MessOn Faculty** | Centralized faculty administration, student management, mess managers, billing, holidays, and notifications. |
| **MessOn Admin** | Mess-side application for operational activities, including meal coupon scanning and validation. |
| **MessOn Backend** | Centralized API, database, and notification infrastructure. |

All applications work together to provide a unified digital mess management experience.

---

## 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

If you would like to contribute:

1. Fork the repository.
2. Create a feature branch.
3. Implement your changes.
4. Test your implementation.
5. Submit a Pull Request with a clear description.

```bash
git checkout -b feature/your-feature-name

git add .

git commit -m "feat: add your feature"

git push origin feature/your-feature-name
```

---

## 👨‍💻 Project Information

| Attribute | Details |
|---|---|
| Project | MessOn Faculty |
| Ecosystem | MessOn |
| Primary Language | Kotlin |
| UI Framework | Compose Multiplatform |
| Architecture | MVVM + Clean Architecture |
| Networking | Ktor Client |
| Dependency Injection | Koin |
| Backend | FastAPI |
| Database | PostgreSQL |
| Notifications | Firebase Cloud Messaging |

---

<p align="center">

  **MessOn — Simplifying Mess Management Through Technology.**

  Built with ❤️ using Kotlin and Compose Multiplatform.

</p>
