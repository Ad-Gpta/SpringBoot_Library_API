package com.assignment.library_api.controllers;

import com.assignment.library_api.exceptions.ResourceNotFoundException;
import com.assignment.library_api.models.Book;
import com.assignment.library_api.models.EBook;
import com.assignment.library_api.services.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class LibraryController {
    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class); //logging parent class

    @Autowired //for dependency injection - class required is annotated with @Service / @Component
    private LibraryService libraryService;

    @GetMapping
    public List<Book> getAllBooks()
    {
        return libraryService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id)
    {
        try { //trying to print something when an exception occurs to handle it properly
            return libraryService.findBookById(id);
        }catch (ResourceNotFoundException e) {
            logger.warn(e.getMessage()); //logging message
            throw e; //throwing exception in the page
        }
    }

    @PostMapping("/addBook")
    public boolean addBook(@RequestBody Book book)
    {
        return libraryService.addBook(book);
    }

    @PostMapping("/addEbook")
    public boolean addEBook(@RequestBody EBook book)
    {
        return libraryService.addBook(book);
    }

    @DeleteMapping("/{id}")
    public boolean removeBook(@PathVariable int id)
    {
        try { //trying to print something when an exception occurs to handle it properly
            return libraryService.removeBook(id);
        }catch (ResourceNotFoundException e) {
            logger.warn(e.getMessage()); //logging message
            throw e; //throwing exception in the page
        }
    }

    @PutMapping("/{id}/borrow")
    public boolean borrowBook(@PathVariable int id)
    {
        try { //trying to print something when an exception occurs to handle it properly
            return libraryService.borrowBook(id);
        }catch (ResourceNotFoundException e) {
            logger.warn(e.getMessage()); //logging message
            throw e; //throwing exception in the page
        }
    }

    @PutMapping("/{id}/return")
    public boolean returnBook(@PathVariable int id)
    {
        try { //trying to print something when an exception occurs to handle it properly
            return libraryService.returnBook(id);
        }catch (ResourceNotFoundException e) {
            logger.warn(e.getMessage()); //logging message
            throw e; //throwing exception in the page
        }
    }
}
