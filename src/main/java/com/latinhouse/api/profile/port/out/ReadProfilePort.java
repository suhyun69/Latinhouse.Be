package com.latinhouse.api.profile.port.out;

import com.latinhouse.api.profile.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReadProfilePort {
    Optional<Profile> findById(String profileId);
    boolean existsById(String profileId);
    Page<Profile> findAll(Pageable pageable);
}
