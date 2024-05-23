# Goal Guru

Goal Guru is a simple and intuitive to-do list Android application designed to help you keep track of your tasks and goals. With Goal Guru, you can list your tasks, set due dates, and mark tasks as completed. The app leverages modern Android development tools and practices, including Hilt for dependency injection, ViewModel for UI-related data handling, and Room for local database storage.

## Features

- **List To-Dos**: Add and view your tasks in a simple list format.
- **Due Dates**: Assign due dates to your tasks to stay on top of deadlines.
- **Task Completion**: Mark tasks as completed with a checkbox.

## Tech Stack

- **Hilt Dependency Injection**: Manages dependencies efficiently and reduces boilerplate code.
- **ViewModel**: Handles UI-related data lifecycle consciously to survive configuration changes.
- **Room Database**: Provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

## Getting Started

### Prerequisites

- Android Studio
- Java 8+
- Gradle 6.5+

### Installation

1. Clone the repository:

    ```sh
    git clone https://github.com/ayush06092002/Goal_Guru
    cd Goal_Guru
    ```

2. Open the project in Android Studio.

3. Build the project:

    - Sync the project with Gradle files.
    - Build the project and run it on an emulator or a physical device.

## Usage

1. **Add a To-Do**: Use the `+` button to add a new task.
2. **Set Due Date**: Select the due date when creating or editing a task.
3. **Complete Task**: Tap the checkbox to mark a task as completed.

## Architecture

Goal Guru follows the MVVM (Model-View-ViewModel) architecture pattern to separate the UI logic from the business logic. 

### Key Components

- **ViewModel**: Manages UI-related data in a lifecycle-conscious way.
- **Repository**: Abstracts the data sources and provides a clean API for data access to the rest of the app.
- **Room Database**: Local database for storing tasks.
- **Hilt**: Simplifies dependency injection and manages the lifecycle of dependencies.

