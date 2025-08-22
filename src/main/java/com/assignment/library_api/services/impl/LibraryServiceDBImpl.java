package com.assignment.library_api.services.impl;

import com.assignment.library_api.exceptions.ResourceNotFoundException;
import com.assignment.library_api.models.Book;
import com.assignment.library_api.repositories.LibraryRepository;
import com.assignment.library_api.services.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceDBImpl implements LibraryService {

    private static final Logger logger = LoggerFactory.getLogger(LibraryServiceDBImpl.class);

    @Autowired
    private LibraryRepository libraryRepository;


    @Override
    public boolean addBook(Book book) {
        book.setCount(1);
        for(Book b: libraryRepository.findAll())
        {
            if(b.equals(book))
            {
                logger.info("Book already in library. Increasing count.");
                b.setCount(b.getCount()+1);
                libraryRepository.save(b);
                return true;
            }
        }
        libraryRepository.save(book);
        logger.info("Book added to library");
        return true;
    }

    @Override
    public boolean removeBook(int id) {
        libraryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book with id "+id+" not found")); //lambda exp
        libraryRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Book> getAllBooks() {
        return libraryRepository.findAll();
    }

    @Override
    public Book findBookById(int id) {
        Optional<Book> book = libraryRepository.findById(id); //makes sure output is object
        if(book.isPresent()) //if object is not null, what to do (so if its null it can be handled using optional)
            return book.get();
        throw new ResourceNotFoundException("Book with id "+id+" not found");
    }

    @Override
    public boolean borrowBook(int id) {
        Book book = findBookById(id);
        if(book.isAvailable())
            {
                logger.info("book with id {} borrowed", id);
                book.setAvailable(false);
                libraryRepository.save(book);
                return true;
            }
        else
            {
                logger.info("book with id {} not available for borrowing", id);
                return false;
            }
    }

    @Override
    public boolean returnBook(int id) {
        Book book = findBookById(id);
        book.setAvailable(true);
        libraryRepository.save(book);
        logger.info("book with id {} returned", id);
        return true;
    }
}
