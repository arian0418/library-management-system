# Library Management System

A JavaFX desktop application for managing a small library. The project supports adding, removing, searching, listing, and saving books through a graphical interface.

## Features

- Add books using ISBN, title, and author
- Prevent duplicate ISBN entries
- Remove books by ISBN
- Search for a book by ISBN
- Search for books by author
- Display the full library collection
- Save and reload library data between sessions
- Display local book cover images by ISBN
- JavaFX graphical user interface

## Technologies

- Java 17
- JavaFX
- Maven
- Java Collections
- File I/O
- Programming with objects

## Project Structure

```
library-management-system/
├── src/main/java/com/arian/library/
│   ├── Book.java
│   ├── Library.java
│   └── LibraryApp.java
├── images/
├── pom.xml
├── .gitignore
└── README.md
```

## Running the Application

Install Java 17+ and Maven, then run:

```bash
mvn clean javafx:run
```

The application creates `library_data.txt` locally when the library is saved.

## Book Covers

Place a JPG inside the `images` directory using the book's ISBN as its filename, such as `images/9780134685991.jpg`. Enter that ISBN and select **Display Cover**.

## Concepts Demonstrated

Java classes and encapsulation, collections, file persistence, input validation, programming around user events, JavaFX UI development, and separation of application logic from the interface.
