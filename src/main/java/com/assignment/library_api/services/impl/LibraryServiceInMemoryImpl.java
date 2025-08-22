package com.assignment.library_api.services.impl;

import com.assignment.library_api.exceptions.ResourceNotFoundException;
import com.assignment.library_api.models.Book;
import com.assignment.library_api.models.EBook;
import com.assignment.library_api.services.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Service
public class LibraryServiceInMemoryImpl implements LibraryService {
    //list of library books
    private static List<Book> libraryBooks = new ArrayList<>();
    private static int bookcount = 4;

    private static final Logger logger = LoggerFactory.getLogger(LibraryServiceInMemoryImpl.class);

    //creating library books
    static{
        libraryBooks.add(new Book(1, "Title1", "Author1", "1", true));
        libraryBooks.add(new Book(2, "Title2", "Author2", "2", false));
        libraryBooks.add(new Book(3, "Title3", "Author3", "3", true));
        libraryBooks.add(new EBook(4, "Title4", "Author1","4", 34, true));
    }

    @Override
    public boolean addBook(Book book)
    {
        for(Book b: libraryBooks)
        {
            if(b.equals(book))
            {
                logger.info("Book already in library. Increasing count.");
                b.setCount(1+b.getCount());
                return true;
            }
        }
        book.setId(++bookcount);
        book.setIsbn(Integer.toString(bookcount));
        libraryBooks.add(book);
        logger.info("Book added to library");
        return true;
    }

    @Override
    public boolean removeBook(int id) throws ResourceNotFoundException{
        //return libraryBooks.removeIf(book -> Objects.equals(book.getId(), id));
        for(Book book: libraryBooks)
        {
            if(book.getId() == id) {
                libraryBooks.remove(book);
                logger.info("Book with id {} removed", id);
                return true;
            }
        }

        logger.error("Book with id {} not found when deleting", id);
        throw new ResourceNotFoundException("Book with id "+id+" not found when borrowing");
    }

    @Override
    public List<Book> getAllBooks() {
        return libraryBooks; //return list of books
    }

    @Override
    public Book findBookById(int id) throws ResourceNotFoundException{
        for(Book book: libraryBooks)
        {
            if(book.getId() == id) {
                logger.info("found book with id: {}", id);
                return book; //return book if found
            }
        }
        logger.error("Book with id {} not found when searching", id);
        throw new ResourceNotFoundException("Book with id "+id+" not found");
    }

    @Override
    public boolean borrowBook(int id) throws ResourceNotFoundException{
        //if book is available, it can be borrowed
        for(Book book: libraryBooks)
        {
            if(book.getId() == id) {
                if(book.isAvailable())
                {
                    logger.info("book with id {} borrowed", id);
                    book.setAvailable(false);
                    return true;
                }
                else
                {
                    logger.info("book with id {} not available for borrowing", id);
                    return false;
                }
            }
        }
        logger.error("Book with id {} not found when borrowing", id);
        throw new ResourceNotFoundException("Book with id "+id+" not found when borrowing");
    }

    @Override
    public boolean returnBook(int id) throws ResourceNotFoundException {
        for(Book book: libraryBooks) {
            if (book.getId() == id) {
                book.setAvailable(true);
                logger.info("book with id {} returned", id);
                return true;
            }
        }
        logger.error("Book with id {} not found when returning", id);
        //return false;
        throw new ResourceNotFoundException("Book with id "+id+" not found when returning");
    }
}
