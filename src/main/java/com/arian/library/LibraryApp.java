package com.arian.library;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class LibraryApp extends Application {
    private final Library library = new Library();
    private final TextField isbnField = new TextField();
    private final TextField titleField = new TextField();
    private final TextField authorField = new TextField();
    private final TextArea outputArea = new TextArea();
    private final ImageView coverView = new ImageView();

    @Override
    public void start(Stage stage) {
        Label heading = new Label("Library Management System");
        heading.setFont(Font.font(22));

        isbnField.setPromptText("ISBN");
        titleField.setPromptText("Book title");
        authorField.setPromptText("Author");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("ISBN:"), isbnField);
        form.addRow(1, new Label("Title:"), titleField);
        form.addRow(2, new Label("Author:"), authorField);

        Button add = new Button("Add Book");
        Button remove = new Button("Remove Book");
        Button searchIsbn = new Button("Search by ISBN");
        Button searchAuthor = new Button("Search by Author");
        Button listAll = new Button("List All Books");
        Button showCover = new Button("Display Cover");
        Button save = new Button("Save Books");

        for (Button button : new Button[]{add, remove, searchIsbn, searchAuthor, listAll, showCover, save}) {
            button.setMaxWidth(Double.MAX_VALUE);
        }

        VBox controls = new VBox(8, form, add, remove, searchIsbn, searchAuthor, listAll, showCover, save);
        controls.setPadding(new Insets(15));
        controls.setPrefWidth(300);

        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        coverView.setFitWidth(170);
        coverView.setFitHeight(230);
        coverView.setPreserveRatio(true);

        VBox results = new VBox(10, outputArea, new Label("Book Cover"), coverView);
        results.setPadding(new Insets(15));
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        HBox body = new HBox(10, controls, results);
        HBox.setHgrow(results, Priority.ALWAYS);

        VBox root = new VBox(10, heading, body);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(body, Priority.ALWAYS);

        add.setOnAction(e -> addBook());
        remove.setOnAction(e -> removeBook());
        searchIsbn.setOnAction(e -> searchByIsbn());
        searchAuthor.setOnAction(e -> searchByAuthor());
        listAll.setOnAction(e -> displayBooks(library.listAllBooks(), "All Books"));
        showCover.setOnAction(e -> displayCover());
        save.setOnAction(e -> saveBooks());

        stage.setOnCloseRequest(e -> library.saveBooksToFile());
        stage.setScene(new Scene(root, 820, 560));
        stage.setTitle("Library Management System");
        stage.show();
    }

    private void addBook() {
        String isbn = isbnField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();

        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty()) {
            showMessage("Please fill in ISBN, title, and author.");
            return;
        }

        Book book = new Book(isbn, title, author);
        if (library.addBook(book)) {
            showMessage("Book added successfully.\n" + book);
            clearFields();
        } else {
            showMessage("A book with that ISBN already exists.");
        }
    }

    private void removeBook() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            showMessage("Enter an ISBN to remove.");
            return;
        }

        showMessage(library.removeBook(isbn)
                ? "Book removed successfully."
                : "No book was found with that ISBN.");
        clearFields();
    }

    private void searchByIsbn() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            showMessage("Enter an ISBN to search.");
            return;
        }

        Book book = library.searchByISBN(isbn);
        showMessage(book == null ? "Book not found." : "Book found:\n" + book);
    }

    private void searchByAuthor() {
        String author = authorField.getText().trim();
        if (author.isEmpty()) {
            showMessage("Enter an author to search.");
            return;
        }
        displayBooks(library.searchByAuthor(author), "Books by " + author);
    }

    private void displayBooks(List<Book> books, String heading) {
        if (books.isEmpty()) {
            showMessage("No matching books found.");
            return;
        }

        StringBuilder result = new StringBuilder(heading).append(":\n\n");
        for (Book book : books) result.append(book).append("\n");
        showMessage(result.toString());
    }

    private void displayCover() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            showMessage("Enter an ISBN to display its cover.");
            return;
        }

        File file = new File("images", isbn + ".jpg");
        if (!file.exists()) {
            coverView.setImage(null);
            showMessage("No cover image found for ISBN " + isbn + ".\nAdd images/" + isbn + ".jpg to display one.");
            return;
        }

        coverView.setImage(new Image(file.toURI().toString()));
        showMessage("Displaying cover for ISBN " + isbn + ".");
    }

    private void saveBooks() {
        showMessage(library.saveBooksToFile()
                ? "Library saved successfully."
                : "The library could not be saved.");
    }

    private void showMessage(String message) {
        outputArea.setText(message);
    }

    private void clearFields() {
        isbnField.clear();
        titleField.clear();
        authorField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
