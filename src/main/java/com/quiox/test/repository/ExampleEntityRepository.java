package com.quiox.test.repository;

import com.quiox.test.entity.ExampleEntity;
import com.quiox.test.projection.ExampleEntityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ExampleEntityRepository extends JpaRepository<ExampleEntity, UUID> {

    List<ExampleEntityProjection> findByActiveIsTrue();
    Optional<ExampleEntityProjection> findDetailsById(UUID id);
}