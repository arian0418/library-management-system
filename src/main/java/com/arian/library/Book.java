package com.arian.library;

import java.util.Objects;

public class Book {
    private final String isbn;
    private final String title;
    private final String author;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn.trim();
        this.title = title.trim();
        this.author = author.trim();
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", isbn, title, author);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return isbn.equals(other.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
