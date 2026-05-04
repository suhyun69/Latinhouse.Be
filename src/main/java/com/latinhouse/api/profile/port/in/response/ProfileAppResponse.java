package com.latinhouse.api.profile.port.in.response;

import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;
import lombok.Getter;

@Getter
public class ProfileAppResponse {

    private final String profileId;
    private final String nickname;
    private final Sex sex;
    private final Boolean isInstructor;

    public ProfileAppResponse(Profile profile) {
        this.profileId = profile.getProfileId();
        this.nickname = profile.getNickname();
        this.sex = profile.getSex();
        this.isInstructor = profile.getIsInstructor();
    }
}
