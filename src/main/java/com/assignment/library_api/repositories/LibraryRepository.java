package com.assignment.library_api.repositories;

import com.assignment.library_api.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Book, Integer> {
}
