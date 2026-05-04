package com.latinhouse.api.profile.adapter.out.persistence.repository;

import com.latinhouse.api.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileJpaEntity, String> {
}
