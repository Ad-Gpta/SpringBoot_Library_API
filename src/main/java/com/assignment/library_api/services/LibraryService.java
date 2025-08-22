package com.assignment.library_api.services;

import com.assignment.library_api.models.Book;

import java.util.List;

public interface LibraryService {
    boolean addBook(Book book);
    boolean removeBook(int id);
    List<Book> getAllBooks();
    Book findBookById(int id);
    boolean borrowBook(int id);
    boolean returnBook(int id);
}
