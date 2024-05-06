package com.quiox.test.repository;

import com.quiox.test.entity.Section;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
  @Query(
    "SELECT s FROM Section s JOIN FETCH s.products WHERE s.id = :sectionId"
  )
  Optional<Section> findByIdWithProducts(Long sectionId);
}
