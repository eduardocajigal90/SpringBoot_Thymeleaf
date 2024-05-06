package com.quiox.test.service;

import com.quiox.test.entity.ElectricalMaterial;
import com.quiox.test.entity.PlumbingTool;
import com.quiox.test.entity.Product;
import com.quiox.test.entity.Section;
import com.quiox.test.projection.ProductRequestDto;
import com.quiox.test.repository.ProductRepository;
import com.quiox.test.repository.SectionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class ProductService {
  @Autowired
  private final ProductRepository productRepository;

  @Autowired
  private final ModelMapper modelMapper;

  @Autowired
  private final SectionRepository sectionRepository;

  /**
   * Saves a product based on the product type specified in the ProductRequestDto.
   * If the product type is ELECTRICAL_MATERIAL, it maps the DTO to ElectricalMaterial and saves it.
   * If the product type is PLUMBING_TOOL, it maps the DTO to PlumbingTool and saves it.
   * If the product type does not match any known type, it returns null.
   *
   * @param product the product request DTO containing details about the product to be saved
   * @return the saved product entity or null if the product type is not recognized
   */
  public Product saveProduct(ProductRequestDto product) {
    Product productToSave = null;
    if ("ELECTRICAL_MATERIAL".equals(product.getProductType().name())) {
      ElectricalMaterial em = modelMapper.map(
        product,
        ElectricalMaterial.class
      );
      productToSave = em;
      return productRepository.save(em);
    } else if ("PLUMBING_TOOL".equals(product.getProductType().name())) {
      PlumbingTool pt = modelMapper.map(product, PlumbingTool.class);
      productToSave = pt;
      return productRepository.save(pt);
    }
    return productToSave;
  }

  public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

  /**
   * Filters products based on various criteria.
   *
   * @param sectionId The ID of the section to which the products belong.
   * @param lot The lot number of the products.
   * @param isFragile A Boolean indicating if the products are fragile.
   * @param color The color of the products.
   * @param minPrice The minimum price of the products.
   * @param maxPrice The maximum price of the products.
   * @param packagingType The type of packaging of the products.
   * @return A list of products that match the specified criteria.
   */
  public List<Product> filterProducts(
    Long sectionId,
    String lot,
    Boolean isFragile,
    String color,
    Double minPrice,
    Double maxPrice,
    String packagingType
  ) {
    return productRepository.findAll(
      (Specification<Product>) (root, query, criteriaBuilder) -> {
        List<Predicate> predicates = new ArrayList<>();
        if (sectionId != null) {
          predicates.add(
            criteriaBuilder.equal(root.get("section").get("id"), sectionId)
          );
        }
        if (lot != null) {
          predicates.add(criteriaBuilder.equal(root.get("lot"), lot));
        }
        if (isFragile != null) {
          predicates.add(
            criteriaBuilder.equal(root.get("isFragile"), isFragile)
          );
        }
        if (color != null) {
          predicates.add(criteriaBuilder.equal(root.get("color"), color));
        }
        if (minPrice != null && maxPrice != null) {
          predicates.add(
            criteriaBuilder.between(root.get("price"), minPrice, maxPrice)
          );
        }
        if (packagingType != null) {
          predicates.add(
            criteriaBuilder.equal(root.get("packagingType"), packagingType)
          );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      }
    );
  }

  /**
   * Adds a specified quantity of a product to a section.
   *
   * @param productId The ID of the product to be added.
   * @param sectionId The ID of the section where the product will be added.
   * @param quantity The quantity of the product to be added.
   * @return The updated product after adding the specified quantity.
   * @throws EntityNotFoundException if either the section or the product is not found.
   */
  public Product addProductToSection(
    Long productId,
    Long sectionId,
    int quantity
  ) {
    Optional<Section> sectionOpt = sectionRepository.findByIdWithProducts(
      sectionId
    );
    if (!sectionOpt.isPresent()) {
      throw new EntityNotFoundException("Section not found");
    }
    Section section = sectionOpt.get();

    Product product = section
      .getProducts()
      .stream()
      .filter(p -> p.getId().equals(productId))
      .findFirst()
      .orElseThrow(() -> new EntityNotFoundException("Product not found"));

    product.setQuantity(product.getQuantity() + quantity);
    return productRepository.save(product);
  }

  public List<Object[]> getProductCountsBySection() {
    return productRepository.countProductsBySection();
  }
}
