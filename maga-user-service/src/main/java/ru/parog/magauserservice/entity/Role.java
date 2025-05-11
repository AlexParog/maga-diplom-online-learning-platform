package ru.parog.magauserservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.parog.onlinelearningplatformmodel.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = Role.TABLE_NAME)
public class Role extends BaseEntity {

    public static final String TABLE_NAME = "roles";

    static class ColumnNames {
        public static final String ROLE_CN_NAME = "name";
    }

    @Column(name = ColumnNames.ROLE_CN_NAME, nullable = false, unique = true)
    private String name;
}
