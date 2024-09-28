# OpenWeatherMap App

Welcome to the OpenWeatherMap App! This Android application is designed to fetch and display weather data from the OpenWeatherMap API, showcasing modern Android development practices, including clean architecture and the MVVM design pattern.


## Features

- **Clean Architecture**: Promotes separation of concerns, making it easier to manage and scale the application. [Learn more](https://en.wikipedia.org/wiki/Clean_architecture).
  
- **MVVM Pattern**: Utilizes ViewModels to manage UI-related data in a lifecycle-conscious way, facilitating data binding and reducing UI logic in Activities and Fragments. [Learn more](https://developer.android.com/jetpack/guide).
  
- **Retrofit**: Implements Retrofit to handle HTTP requests for weather data, ensuring a smooth and efficient data retrieval process. [Learn more](https://square.github.io/retrofit/).
  
- **Room Database**: Uses Room for local database management, enabling offline access to weather data and improving user experience. [Learn more](https://developer.android.com/training/data-storage/room).
  
- **Mappers**: Implements mappers to convert data models between the API response and the local database format, ensuring a clean separation of concerns and data consistency.
  
- **ViewBinding**: Utilizes ViewBinding to simplify UI interactions and eliminate the need for `findViewById`, leading to more readable and safer code. [Learn more](https://developer.android.com/topic/libraries/view-binding).
  
- **Modular Structure**: The app is organized into modules, enhancing maintainability and allowing for easier feature additions and testing. [Learn more about modular architecture](https://developer.android.com/topic/libraries/architecture).
  
- **Union Architecture**: Integrates various architectural best practices to create a flexible and scalable structure. [Learn more](https://medium.com/@krishnacv/understanding-union-architecture-in-android-4c3ef50bbd18).
  
- **SOLID Principles**: Adheres to SOLID principles to ensure code is easy to understand, maintain, and extend. [Learn more](https://en.wikipedia.org/wiki/SOLID).
  
- **Unit Testing**: Features comprehensive unit tests to validate business logic and ensure reliability. [Learn more](https://developer.android.com/training/testing/unit-testing).
  
- **UI Testing**: Implements UI tests to verify user interactions and ensure a high-quality user experience. [Learn more](https://developer.android.com/training/testing/espresso).
  
- **Single Activity Architecture**: Utilizes a single Activity for the entire app, simplifying navigation and lifecycle management. [Learn more](https://developer.android.com/jetpack/guide/navigation).
  
- **Hilt Dependency Injection**: Leverages Hilt for dependency injection, simplifying code and improving testability. [Learn more](https://developer.android.com/training/dependency-injection/hilt-android).


![image](https://miro.medium.com/v2/resize:fit:772/1*wOmAHDN_zKZJns9YDjtrMw.jpeg)
## Requirements

- **Android Studio**: Make sure you have the latest version installed.
- **Android SDK**: Ensure you have the required SDK packages.
- **Gradle**: The project uses Gradle for build management.

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/mohamedmzd0/OpenWeatherMap.git
