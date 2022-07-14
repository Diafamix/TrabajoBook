package com.optimissa.BookShelfApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 800000, nullable = false)
    private String description;

    @Column(nullable = false)
    private Date publication;

    @Column(nullable = false)
    private String family;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private double price;

    @Builder
    public BookDetails(String description, Date publication, String family, String language, double price) {
        this.description = description;
        this.publication = publication;
        this.family = family;
        this.language = language;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDetails that = (BookDetails) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
