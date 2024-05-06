package com.quiox.test.repository;

import com.quiox.test.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository
  extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
  @Query(
    value = "SELECT p.section_id as sectionId, p.name as productName, SUM(p.quantity) as quantity FROM product p GROUP BY p.section_id, p.name",
    nativeQuery = true
  )
  List<Object[]> countProductsBySection();
}
