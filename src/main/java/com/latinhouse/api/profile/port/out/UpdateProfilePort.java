package com.latinhouse.api.profile.port.out;

import com.latinhouse.api.profile.domain.Profile;

public interface UpdateProfilePort {
    Profile update(Profile profile);
}
