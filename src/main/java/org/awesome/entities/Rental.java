package org.awesome.entities;

import jakarta.persistence.*;
import lombok.*;
import org.awesome.constants.RentalStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    @Id @GeneratedValue
    private Long rentalNo;

    @ToString.Exclude
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="bookId")
    private RentalBook book;

    private String bookNm;

    @Enumerated(EnumType.STRING)
    private RentalStatus status = RentalStatus.RENT;

    @ToString.Exclude
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="userNo")
    private User user;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private LocalDate rentDt;

    @Temporal(TemporalType.DATE)
    private LocalDate returnDt;

    @Temporal(TemporalType.DATE)
    private LocalDate realRtDt;
}
