package org.awesome.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modDt;
}

