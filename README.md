### What it is

HabitPal is an Android app that helps users build and track habits. It lets you create habits, group them, log your progress, and visualize streaks. The app is built using Kotlin, Jetpack Compose for the UI, and Room for local database storage.

---

### How it works (high-level)

1. **App Structure**
    - The app has a single Android module (in `:app`).
    - Uses Jetpack Compose for modern, declarative UI.
    - The data is stored locally using Room (SQLite database).

2. **Main Components**
    - **Models:** These define data like Habit, HabitGroup, and HabitLog.
    - **Repositories:** These handle operations with the database (CRUD for habits, groups, logs).
    - **ViewModels:** These manage UI state and business logic, and talk to repositories.
    - **Screens/Composables:** These are the UI pieces (like the main screen, charts, dialogs).

3. **Data Flow**
    - When you open the app, the MainActivity sets up ViewModels and shows the MainScreen.
    - UI screens observe the ViewModel state (using Kotlin flows).
    - Adding, editing, or deleting habits/groups is done via ViewModels, which update the database through repositories.
    - The UI automatically updates when the data changes.

4. **Features**
    - **Create & Manage Habits:** Add, edit, archive, and delete habits. Habits can belong to groups.
    - **Habit Groups:** Organize habits into groups for better tracking.
    - **Logging:** Record your habit completions.
    - **Streaks & Charts:** Visualize your progress with streak counts and line charts.

5. **Persistence**
    - All user data (habits, logs, groups) is stored locally using Room database, so it works offline.

6. **Tech Stack**
    - Kotlin, Jetpack Compose, Room, Hilt (for dependency injection), AndroidX libraries.
