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
@Builder
public class RentBook {

    @Id
    @Column(name = "SAMPLE_ID", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "SAMPLE_ID", nullable = false)
    private Sample sample;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentBook rentBook = (RentBook) o;
        return id == rentBook.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
