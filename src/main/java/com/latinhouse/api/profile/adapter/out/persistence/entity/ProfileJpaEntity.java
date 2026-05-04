package com.latinhouse.api.profile.adapter.out.persistence.entity;

import com.latinhouse.api.profile.domain.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileJpaEntity {

    @Id
    private String profileId;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private Boolean isInstructor;
}
