package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends CrudRepository<ProfileEntity, UUID>, PagingAndSortingRepository<ProfileEntity,UUID> {

    @Query("from ProfileEntity where visible=true and email=?1")
    Optional<ProfileEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status=?1 where id=?2 and visible=true ")
    void changeStatus(ProfileStatus profileStatus, UUID id);

    @Query("from ProfileEntity where visible=true")
    Iterable<ProfileEntity> findAll();

    @Query("from ProfileEntity where visible=true")
    Page<ProfileEntity> findAllByPage(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false where id=?1")
    void makeDeleteById(UUID id);
}
