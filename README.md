# ProductApp: Android Product Listing and Details Application

## Overview

ProductApp is an Android application designed to manage product information for users.
The project is written entirely in Kotlin and follows the MVI (Model-View-Intent) architecture pattern. It leverages modern technologies such as Coroutines for asynchronous operations, Room DB for local storage, Retrofit for network requests, and includes comprehensive unit tests.
The application also adheres to Clean Architecture principles and is structured as a multi-module project for better maintainability and scalability.

## Screens

### Product List Screen

- Displays a list of products fetched from an external data source.
- Each product is represented as an item within a RecyclerView.
- Utilizes RecyclerView and RecyclerView Adapter for efficient data presentation.
- XML layouts are used for defining the UI elements of the product list item.

### Product Details Screen

- Shows detailed information about the selected product.
- Information includes product name, description, price, etc.

## Implementation Details

- **Architecture:** Follows the MVI (Model-View-Intent) architecture pattern for robustness and maintainability. Adheres to Clean Architecture principles.
- **Multi-Module:** Organizes the codebase into separate modules for better maintainability and scalability.
- **Kotlin Coverage:** Achieves 100% Kotlin coverage across the entire codebase.
- **XML UI Development:** The app's user interface is crafted using XML layouts, prioritizing flexibility and maintainability in design.
- **Coroutines:** Utilizes Coroutines for managing asynchronous operations, ensuring smooth app performance.
- **Room DB:** Integrates Room DB for local data storage, enabling offline access and improved performance.
- **Retrofit:** Implements Retrofit for network requests and seamless communication with the backend server.
- **Unit Testing:** Includes comprehensive unit tests covering critical components and functionalities.

