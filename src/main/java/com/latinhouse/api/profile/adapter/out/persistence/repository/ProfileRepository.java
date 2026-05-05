package com.latinhouse.api.profile.adapter.out.persistence.repository;

import com.latinhouse.api.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProfileRepository extends JpaRepository<ProfileJpaEntity, String>,
        JpaSpecificationExecutor<ProfileJpaEntity> {
}
