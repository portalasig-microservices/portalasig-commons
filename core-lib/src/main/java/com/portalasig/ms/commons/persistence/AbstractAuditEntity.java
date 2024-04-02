package com.portalasig.ms.commons.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Data
@MappedSuperclass
public abstract class AbstractAuditEntity implements Serializable {

    @Column(name = "created_by")
    @CreatedBy
    @NotNull
    private Long createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    @NotNull
    private Long updatedBy;

    @Column(name = "created_date")
    @CreatedDate
    @NotNull
    private Instant createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    @NotNull
    private Instant updatedDate;
}
