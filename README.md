## HabitPal Overview

### What It Is

**HabitPal** is a native Android application that helps users build and track habits. Users can create habits, group them, log progress, and visualize streaks. It is developed in **Kotlin**, utilizing **Jetpack Compose** for UI and **Room** for local data storage.

---

### High-Level Architecture

#### 1. App Structure

* Single Android module (`:app`)
* Declarative UI built with **Jetpack Compose**
* Local data storage via **Room (SQLite)**

#### 2. Main Components

* **Models**: Define core data entities such as `Habit`, `HabitGroup`, and `HabitLog`.
* **Repositories**: Handle database interactions (CRUD operations).
* **ViewModels**: Manage UI state and business logic; act as intermediaries between the UI and repositories.
* **Screens/Composables**: Composable UI elements for various screens like main dashboard, charts, dialogs, etc.

#### 3. Data Flow

* `MainActivity` initializes ViewModels and loads the `MainScreen`.
* UI observes ViewModel state using Kotlin **Flow**.
* Habit/group operations are performed via ViewModels, which in turn update the database through Repositories.
* UI automatically reflects any data changes.

---

### Key Features

* **Create & Manage Habits**: Add, edit, archive, or delete habits. Habits can be grouped.
* **Habit Groups**: Organize habits into logical groups.
* **Logging**: Record habit completions easily.
* **Streaks & Charts**: Visualize progress through streak counters and line charts.

---

### Persistence

All user data is stored locally using **Room**, ensuring full offline functionality.

---

### Tech Stack

* **Language**: Kotlin
* **UI**: Jetpack Compose
* **Database**: Room
* **Dependency Injection**: Hilt
* **Libraries**: AndroidX components

---

## Technical Decisions

### Kotlin & Jetpack Compose

**Pros:**

* Native support and better integration
* Jetpack Compose simplifies and modernizes UI development
* Fewer external dependencies compared to cross-platform solutions
* High performance

**Trade-offs:**

* Building simple features may take more time due to verbose syntax

### Room Database

**Pros:**

* Simple and efficient for well-defined structures
* Easy integration with ViewModels and Flows

**Trade-offs:**

* Opinionated setup
* Complex or nested data structures require extra handling and care

---

## Potential Improvements

* Begin with a thorough and intentional **UI/UX design** process.
* Refactor the project into a **native-compliant directory structure**.
* Use **Dagger Hilt** more extensively for dependency injection to simplify code and avoid "prop drilling" (commonly referenced in JS development).
* Current state management is solid but would benefit from better file organization.

---
