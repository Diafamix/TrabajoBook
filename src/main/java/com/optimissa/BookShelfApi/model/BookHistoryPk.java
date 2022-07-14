package com.optimissa.BookShelfApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookHistoryPk implements Serializable {

    @Column(name = "user_mail", nullable = false)
    private String mail;

    @Column(name = "book_id", nullable = false)
    private String bookId;

}
