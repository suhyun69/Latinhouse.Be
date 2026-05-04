package com.latinhouse.api.profile.adapter.out.persistence;

import com.latinhouse.api.profile.adapter.out.persistence.mapper.ProfileMapper;
import com.latinhouse.api.profile.adapter.out.persistence.repository.ProfileRepository;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.port.out.CreateProfilePort;
import com.latinhouse.api.profile.port.out.DeleteProfilePort;
import com.latinhouse.api.profile.port.out.ReadProfilePort;
import com.latinhouse.api.profile.port.out.UpdateProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProfilePersistenceAdapter implements CreateProfilePort, ReadProfilePort, UpdateProfilePort, DeleteProfilePort {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    @Override
    public Profile create(Profile profile) {
        return profileMapper.mapToDomainEntity(
                profileRepository.save(profileMapper.mapToJpaEntity(profile)));
    }

    @Override
    public boolean existsById(String profileId) {
        return profileRepository.existsById(profileId);
    }

    @Override
    public Optional<Profile> findById(String profileId) {
        return profileRepository.findById(profileId)
                .map(profileMapper::mapToDomainEntity);
    }

    @Override
    public Page<Profile> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable)
                .map(profileMapper::mapToDomainEntity);
    }

    @Override
    public Profile update(Profile profile) {
        return profileMapper.mapToDomainEntity(
                profileRepository.save(profileMapper.mapToJpaEntity(profile)));
    }

    @Override
    public void deleteById(String profileId) {
        profileRepository.deleteById(profileId);
    }
}
