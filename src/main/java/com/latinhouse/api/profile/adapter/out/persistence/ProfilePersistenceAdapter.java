package com.latinhouse.api.profile.adapter.out.persistence;

import com.latinhouse.api.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import com.latinhouse.api.profile.adapter.out.persistence.mapper.ProfileMapper;
import com.latinhouse.api.profile.adapter.out.persistence.repository.ProfileRepository;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.port.in.request.FindProfileAppRequest;
import com.latinhouse.api.profile.port.out.CreateProfilePort;
import com.latinhouse.api.profile.port.out.DeleteProfilePort;
import com.latinhouse.api.profile.port.out.ReadProfilePort;
import com.latinhouse.api.profile.port.out.UpdateProfilePort;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
    public Page<Profile> findAll(Pageable pageable, FindProfileAppRequest searchReq) {
        Specification<ProfileJpaEntity> spec = buildSpecification(searchReq);
        return profileRepository.findAll(spec, pageable)
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

    /**
     * FindProfileAppRequest의 조건을 JPA Specification으로 변환한다.
     * null 필드는 조건에서 제외한다.
     *
     * nickname: LIKE lower('%nickname%') — 부분 일치, 대소문자 무시
     * sex     : equal (enum name 비교)
     * isInstructor: equal
     */
    private Specification<ProfileJpaEntity> buildSpecification(FindProfileAppRequest searchReq) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchReq.getNickname() != null) {
                String pattern = "%" + searchReq.getNickname().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("nickname")), pattern));
            }

            if (searchReq.getSex() != null) {
                predicates.add(cb.equal(root.get("sex"), searchReq.getSex()));
            }

            if (searchReq.getIsInstructor() != null) {
                predicates.add(cb.equal(root.get("isInstructor"), searchReq.getIsInstructor()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
