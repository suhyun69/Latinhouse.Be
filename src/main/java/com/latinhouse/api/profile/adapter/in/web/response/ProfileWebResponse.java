package com.latinhouse.api.profile.adapter.in.web.response;

import com.latinhouse.api.profile.domain.Sex;
import com.latinhouse.api.profile.port.in.response.ProfileAppResponse;
import lombok.Getter;

@Getter
public class ProfileWebResponse {

    private final String profileId;
    private final String nickname;
    private final Sex sex;
    private final Boolean isInstructor;

    public ProfileWebResponse(ProfileAppResponse appResponse) {
        this.profileId = appResponse.getProfileId();
        this.nickname = appResponse.getNickname();
        this.sex = appResponse.getSex();
        this.isInstructor = appResponse.getIsInstructor();
    }
}
