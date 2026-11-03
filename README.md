# 🎓 MessOn Student

### Smart Student Mess Management Application

**MessOn Student** is a student-facing application developed as part of the MessOn Digital Mess Management Ecosystem. It provides students with a centralized platform to manage their daily mess activities, including meal coupons, meal preferences, menu information, complaints, billing, and notifications.

Built using **Kotlin and Compose Multiplatform**, the application follows modern software engineering principles, including MVVM, Clean Architecture, dependency injection, and reactive state management.

MessOn Student connects with the centralized MessOn backend to provide a seamless digital mess management experience.

---

## 📌 Table of Contents

- [About the Project](#-about-the-project)
- [Key Features](#-key-features)
- [Application Architecture](#-application-architecture)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Core Modules](#-core-modules)
- [Authentication](#-authentication)
- [Meal Coupon System](#-meal-coupon-system)
- [Complaint Management](#-complaint-management)
- [Push Notification System](#-push-notification-system)
- [Backend Integration](#-backend-integration)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Security](#-security)
- [Development Guidelines](#-development-guidelines)
- [Future Enhancements](#-future-enhancements)
- [MessOn Ecosystem](#-messon-ecosystem)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🚀 About the Project

Traditional mess management often involves manual processes for meal tracking, coupon distribution, menu communication, complaint handling, and billing.

MessOn Student aims to digitize these processes by providing students with a unified application through which they can access essential mess-related services.

The application is part of a larger ecosystem consisting of Student, Faculty, and Mess applications, all communicating with a centralized backend.

### Project Objectives

- Digitize everyday student mess activities.
- Simplify meal coupon generation and validation.
- Allow students to manage their meal preferences.
- Provide centralized access to mess menus.
- Improve communication between students and mess administration.
- Enable transparent complaint tracking.
- Simplify access to billing information.
- Deliver real-time announcements through push notifications.

---

## ✨ Key Features

### 1. Google Authentication

Provides secure and convenient student authentication through Google Sign-In.

Key capabilities:

- Google account authentication.
- Integration with Android Credential Manager.
- Simplified sign-in experience.
- Authenticated access to student services.

### 2. Digital Meal Coupon System

Provides students with digital meal coupons for mess access.

Key capabilities:

- QR-based meal coupon generation.
- Digital coupon representation.
- Support for breakfast, lunch, and dinner.
- Coupon validation through the mess-side application.
- Integration with backend coupon management.
- Reduced dependency on traditional physical coupons.

### 3. Meal Preferences

Allows students to manage their meal preferences digitally.

Key capabilities:

- Submit meal preferences.
- Manage preferences for different meal slots.
- View and update preference information.
- Synchronize preferences with the backend.

This helps mess administration understand student meal requirements and plan food preparation accordingly.

### 4. Mess Menu Management

Provides students with access to mess menu information.

Key capabilities:

- View daily mess menus.
- Access meal-related information.
- Stay informed about menu changes.
- Receive menu update notifications.

### 5. Complaint Management

Provides a structured platform for students to submit and track mess-related complaints.

Key capabilities:

- Submit mess complaints.
- View submitted complaints.
- Track complaint status.
- Monitor pending, in-progress, and resolved complaints.
- React to complaints using like and dislike functionality.
- Stay informed about complaint progress.

This promotes transparency and improves communication between students and mess administration.

### 6. Mess Billing

Provides students with access to billing-related information and CSV-based billing functionality.

Key capabilities:

- Access billing information.
- Handle billing-related CSV files.
- Support file-based billing workflows.
- Integrate with the centralized mess management backend.

File handling is implemented using FileKit.

### 7. Push Notifications

Keeps students informed about important mess-related announcements through Firebase Cloud Messaging (FCM).

Notification examples:

- Mess menu updates.
- Meal announcements.
- Important mess notices.
- Administrative announcements.

Key capabilities:

- Receive push notifications.
- Register device FCM tokens.
- Synchronize device registration with the backend.
- Support notification delivery through Firebase.

### 8. Cross-Platform Development

Built using Compose Multiplatform, allowing shared Kotlin application logic and UI development across supported platforms.

Benefits:

- Shared codebase.
- Reusable UI components.
- Consistent application design.
- Reduced code duplication.
- Easier long-term maintenance.

---

## 🏗️ Application Architecture

MessOn Student follows **Clean Architecture combined with the MVVM (Model-View-ViewModel) pattern**.

The architecture separates UI, business logic, and data operations to improve maintainability, testability, and scalability.

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
- Remote data sources.
- Backend integration.

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Compose Multiplatform | Cross-platform UI development |
| Material 3 | UI components and design system |
| MVVM | Presentation architecture |
| Clean Architecture | Separation of concerns |
| Kotlin Coroutines | Asynchronous operations |
| Kotlin Flow / StateFlow | Reactive state management |
| Ktor Client | HTTP networking |
| Koin | Dependency injection |
| Android Credential Manager | Google authentication |
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
com.messon.student.app/
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

*Note: This is a representative architecture overview. Refer to the repository for the exact package and directory organization.*

---

## 🔐 Authentication

MessOn Student integrates Google authentication using Android Credential Manager.

### Authentication Flow

```text
       Student Application
                |
          Sign-In Request
                |
       Credential Manager
                |
          Google Account
                |
       Authentication Result
                |
          Backend Integration
                |
        Authenticated Student
                |
         Access MessOn Services
```

The authentication flow provides students with a convenient way to access their mess-related services.

---

## 🎫 Meal Coupon System

The digital meal coupon system replaces traditional manual coupon handling with QR-based meal access.

### Coupon Generation Flow

```text
          Student Application
                   |
            Authenticated User
                   |
             Coupon Request
                   |
             FastAPI Backend
                   |
          Coupon Generation Logic
                   |
             PostgreSQL
                   |
             Coupon Response
                   |
             QR Generation
                   |
          Student Displays QR
                   |
           MessOn Mess App
                   |
             QR Scanning
                   |
           Backend Validation
                   |
            Meal Verification
```

### Supported Meal Slots

- Breakfast
- Lunch
- Dinner

The backend manages coupon records, meal assignments, and validity rules.

---

## 📝 Complaint Management

The complaint management module provides a structured communication channel between students and mess administration.

### Complaint Lifecycle

```text
        Student Submits Complaint
                   |
                   |
             Pending Status
                   |
                   |
          Faculty / Admin Review
                   |
                   |
          In-Progress Status
                   |
                   |
           Complaint Resolution
                   |
                   |
            Resolved Status
```

Students can monitor complaint progress and use like/dislike reactions to interact with complaint records.

---

## 🔔 Push Notification System

MessOn Student integrates Firebase Cloud Messaging to receive important announcements from the MessOn ecosystem.

### Notification Architecture

```text
          Faculty / Admin App
                   |
           Notification Request
                   |
             FastAPI Backend
                   |
            Firebase Admin SDK
                   |
          Firebase Cloud Messaging
                   |
             Student Devices
                   |
           Push Notifications
```

### Device Registration

The student application manages FCM device registration to support reliable notification delivery.

Key responsibilities:

- Obtain device FCM tokens.
- Register device tokens with the backend.
- Synchronize device registration.
- Handle token refresh.
- Support device unregistration when appropriate.

The backend maintains device registration information for notification delivery.

---

## 🔗 Backend Integration

MessOn Student communicates with the centralized MessOn backend.

### Backend Technology

- Python
- FastAPI
- PostgreSQL
- Firebase Cloud Messaging

### Backend Responsibilities

- Student authentication and records.
- Meal preference management.
- Coupon generation and validation.
- Menu management.
- Complaint management.
- Billing-related operations.
- Device token registration.
- Push notification delivery.
- Coupon analytics.

The backend acts as the central data and communication layer connecting the Student, Faculty, and Mess applications.

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
git clone <YOUR_STUDENT_REPOSITORY_URL>
```

### 2. Navigate to the Project

```bash
cd MessOn-Student
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
2. Add the appropriate Firebase configuration files.
3. Enable Firebase Cloud Messaging.
4. Configure the backend Firebase Admin SDK securely.
5. Verify device token registration.

### 6. Configure Google Authentication

1. Configure Google authentication for the Firebase or Google Cloud project used by MessOn.
2. Configure the required OAuth credentials.
3. Register the appropriate application identifiers.
4. Verify the authentication configuration.

Never commit private credentials or API secrets to GitHub.

### 7. Build the Application

```bash
./gradlew build
```

To install the Android application:

```bash
./gradlew installDebug
```

---

## 🔐 Security

Security is important because the application handles student information and mess-related records.

Recommended security practices:

- Use secure HTTPS communication.
- Never hardcode sensitive credentials.
- Keep Firebase service-account credentials on the backend.
- Validate authentication tokens on the backend.
- Enforce student-level authorization.
- Validate coupon ownership and status on the server.
- Avoid trusting client-side coupon validation alone.
- Protect student data from unauthorized access.
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
10. Validate important operations on the backend.

---

## 🚧 Future Enhancements

Potential areas for further development include:

- Advanced meal consumption analytics.
- Improved billing history and reports.
- Enhanced complaint tracking.
- More detailed notification preferences.
- Offline-friendly application support.
- Improved coupon analytics.
- Enhanced student dashboards.
- Automated testing and CI/CD integration.

---

## 🌐 MessOn Ecosystem

MessOn Student is one of three interconnected applications.

| Application | Description |
|---|---|
| **MessOn Student** | Student-facing application for meal coupons, preferences, menus, complaints, billing, and notifications. |
| **MessOn Faculty** | Faculty administration for student management, mess managers, billing, holidays, complaints, and notifications. |
| **MessOn Mess** | Mess-side application for operational activities, including meal coupon scanning and validation. |
| **MessOn Backend** | Centralized API, database, and notification infrastructure. |

All applications communicate through a centralized backend to provide a unified digital mess management experience.

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
| Project | MessOn Student |
| Ecosystem | MessOn |
| Package | `com.messon.student.app` |
| Primary Language | Kotlin |
| UI Framework | Compose Multiplatform |
| Architecture | MVVM + Clean Architecture |
| Networking | Ktor Client |
| Dependency Injection | Koin |
| Backend | FastAPI |
| Database | PostgreSQL |
| Authentication | Google Credential Manager |
| Notifications | Firebase Cloud Messaging |

---

<p align="center">

**MessOn — Simplifying Mess Management Through Technology.**

Built with ❤️ using Kotlin and Compose Multiplatform.

</p>
