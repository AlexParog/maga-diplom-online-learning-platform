package ru.parog.onlinelearningplatformmodel.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class BaseEntity implements IEntity<Long> {

    static class ColumnNames {
        public static final String BASE_CN_CREATED_AT = "created_at";
        public static final String BASE_CN_UPDATED_AT = "updated_at";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ColumnNames.BASE_CN_CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = ColumnNames.BASE_CN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
