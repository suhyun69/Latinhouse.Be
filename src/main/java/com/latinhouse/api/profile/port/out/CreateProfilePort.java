package com.latinhouse.api.profile.port.out;

import com.latinhouse.api.profile.domain.Profile;

public interface CreateProfilePort {
    Profile create(Profile profile);
}
