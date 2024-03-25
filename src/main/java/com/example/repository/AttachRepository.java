package com.example.repository;

import com.example.entity.AttachEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AttachRepository extends CrudRepository<AttachEntity, UUID>, PagingAndSortingRepository<AttachEntity,UUID> {

}
