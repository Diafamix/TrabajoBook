package com.optimissa.BookShelfApi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
public class BookHistory {

    @EmbeddedId
    private BookHistoryPk id = new BookHistoryPk();

    private Date date;

    @Enumerated(EnumType.STRING)
    private Type type;

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Book book;

    private int quantity;

    @MapsId("mail")
    @ManyToOne
    @JoinColumn(name = "user_mail", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @Builder
    public BookHistory(Date date, Type type, Book book, int quantity, User user) {
        this.date = date;
        this.type = type;
        this.book = book;
        this.quantity = quantity;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookHistory that = (BookHistory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum Type {
        BUY,
        SELL,
        RENT,
        END_RENT
    }

}



