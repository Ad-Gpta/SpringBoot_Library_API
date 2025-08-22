package com.assignment.library_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data //Lombok annotation for generating getters and setters
@AllArgsConstructor //Lombok generates constructor having all attributes
@ToString
@Entity
public class EBook extends Book {

    @Column
    private int fileSize;

    public EBook(int id, String title, String author, String isbn, int fileSize, boolean available) {
        super(id, title, author, isbn, available);
        this.fileSize = fileSize;
    }
}