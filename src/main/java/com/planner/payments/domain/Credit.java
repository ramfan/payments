package com.planner.payments.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "credit")
@EqualsAndHashCode
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column
    @Getter
    @Setter
    private String name;

    @Column(name = "credit_size", nullable = false)
    @Getter
    @Setter
    private Long creditSize;

    @Column
    @Getter
    @Setter
    private Float percent;

    @Column(name = "start_date", updatable = false)
    @Getter
    @Setter
    private LocalDate startDate;

    @Column(name = "months_count")
    @Getter
    @Setter
    private Integer monthsCount;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    @Getter
    @Setter
    private Person borrower;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return id.equals(credit.id) && Objects.equals(name, credit.name) && creditSize.equals(credit.creditSize) && percent.equals(credit.percent) && startDate.equals(credit.startDate) && monthsCount.equals(credit.monthsCount) && borrower.equals(credit.borrower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creditSize, percent, startDate, monthsCount, borrower);
    }
}
