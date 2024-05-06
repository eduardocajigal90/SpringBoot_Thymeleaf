package com.quiox.test.service;

import com.quiox.test.entity.ElectricalMaterial;
import com.quiox.test.entity.PlumbingTool;
import com.quiox.test.entity.Product;
import com.quiox.test.entity.Section;
import com.quiox.test.projection.SectionRequestDto;
import com.quiox.test.repository.ProductRepository;
import com.quiox.test.repository.SectionRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class SectionService {
  @Autowired
  private final SectionRepository sectionRepository;

  @Autowired
  private final ModelMapper modelMapper;

  @Autowired
  private final ProductRepository productRepository;

  /**
   * Saves a new section with the specified details from the SectionRequestDto.
   * This method maps each product DTO to its respective entity (ElectricalMaterial or PlumbingTool),
   * saves them to the database, and associates them with the new section.
   *
   * @param sectionDto the data transfer object containing section details and a list of products
   * @return the newly created and saved Section entity
   */
  public Section saveSection(SectionRequestDto sectionDto) {
    Section sectionToSave = new Section();
    List<Product> products = sectionDto
      .getProducts()
      .stream()
      .map(
        productDto -> {
          Product product = null;
          if ("ELECTRICAL_MATERIAL".equals(productDto.getProductType().name())) {
            product = modelMapper.map(productDto, ElectricalMaterial.class);
          } else if ("PLUMBING_TOOL".equals(productDto.getProductType().name())) {
            product = modelMapper.map(productDto, PlumbingTool.class);
          }
          if (product != null) {
            productRepository.save(product);
          }
          return product;
        }
      )
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
    sectionToSave.setProducts(products);
    sectionToSave.setSizeInSquareMeters(sectionDto.getSizeInSquareMeters());
    return sectionRepository.save(sectionToSave);
  }

  public Optional<Section> getSectionById(Long id) {
    return sectionRepository.findById(id);
  }

  public List<Section> getAllSections() {
    return sectionRepository.findAll();
  }

  /**
   * Attempts to delete a section by its ID if it has no associated products.
   * 
   * @param id the ID of the section to be deleted
   * @return true if the section was deleted, false if the section has products or does not exist
   */
  public boolean deleteSection(Long id) {
    return sectionRepository
      .findById(id)
      .map(
        section -> {
          if (section.getProducts().isEmpty()) {
            sectionRepository.deleteById(id);
            return true;
          }
          return false;
        }
      )
      .orElse(false);
  }
}
