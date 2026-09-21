package com.arian.library;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Library {
    private static final String FILE_NAME = "library_data.txt";
    private final ArrayList<Book> books = new ArrayList<>();

    public Library() {
        loadBooksFromFile();
    }

    public boolean addBook(Book book) {
        if (book == null || searchByISBN(book.getIsbn()) != null) return false;
        books.add(book);
        return true;
    }

    public boolean removeBook(String isbn) {
        Book book = searchByISBN(isbn);
        return book != null && books.remove(book);
    }

    public Book searchByISBN(String isbn) {
        if (isbn == null) return null;
        String target = isbn.trim();
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(target)) return book;
        }
        return null;
    }

    public List<Book> searchByAuthor(String author) {
        ArrayList<Book> result = new ArrayList<>();
        if (author == null) return result;
        String target = author.trim();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(target)) result.add(book);
        }
        return result;
    }

    public List<Book> listAllBooks() {
        return Collections.unmodifiableList(books);
    }

    private void loadBooksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|", -1);
                if (parts.length == 3) {
                    addBook(new Book(parts[0], parts[1], parts[2]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading library data: " + e.getMessage());
        }
    }

    public boolean saveBooksToFile() {
        try (PrintWriter output = new PrintWriter(FILE_NAME)) {
            for (Book book : books) {
                output.printf("%s|%s|%s%n",
                        sanitize(book.getIsbn()),
                        sanitize(book.getTitle()),
                        sanitize(book.getAuthor()));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error saving library data: " + e.getMessage());
            return false;
        }
    }

    private String sanitize(String value) {
        return value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }
}
