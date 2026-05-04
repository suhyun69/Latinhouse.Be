package com.latinhouse.api.profile.adapter.out.persistence.mapper;

import com.latinhouse.api.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import com.latinhouse.api.profile.domain.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileJpaEntity mapToJpaEntity(Profile profile) {
        return ProfileJpaEntity.builder()
                .profileId(profile.getProfileId())
                .nickname(profile.getNickname())
                .sex(profile.getSex())
                .isInstructor(profile.getIsInstructor())
                .build();
    }

    public Profile mapToDomainEntity(ProfileJpaEntity entity) {
        return Profile.builder()
                .profileId(entity.getProfileId())
                .nickname(entity.getNickname())
                .sex(entity.getSex())
                .isInstructor(entity.getIsInstructor())
                .build();
    }
}
