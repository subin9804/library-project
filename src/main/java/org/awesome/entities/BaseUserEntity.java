package org.awesome.entities;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

public class BaseUserEntity extends BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private Long CreateBy;

    @LastModifiedBy
    @Column(insertable = false)
    private Long modifiedBy;
}
