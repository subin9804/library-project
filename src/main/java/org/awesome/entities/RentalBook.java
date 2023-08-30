package org.awesome.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.awesome.constants.RentalStatus;
import org.awesome.constants.RentalType;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class RentalBook extends BaseEntity {
    @Id
    private String bookId;

    @Column(length=45, nullable = false)
    private String gid;

    @Enumerated(EnumType.STRING)
    @Column(length=30, nullable = false)
    private RentalStatus status = RentalStatus.RETURN;

    @Enumerated(EnumType.STRING)
    @Column(length=30, nullable = false)
    private RentalType rentalType = RentalType.PAPER;

    @Column(length=100, nullable = false)
    private String bookNm;

    @Column(length=45, nullable = false)
    private String author;

    @Column(length=45, nullable = false)
    private String publisher;

    @Lob
    private String description;
}
