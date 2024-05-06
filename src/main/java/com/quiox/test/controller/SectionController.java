package com.quiox.test.controller;

import com.quiox.test.entity.Section;
import com.quiox.test.projection.SectionRequestDto;
import com.quiox.test.service.SectionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sections")
public class SectionController {
  @Autowired
  private final SectionService sectionService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Section> createSection(
    @RequestBody SectionRequestDto section
  ) {
    Section createdSection = sectionService.saveSection(section);
    return ResponseEntity.ok(createdSection);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Section> getSectionById(@PathVariable Long id) {
    return sectionService
      .getSectionById(id)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<Section>> getAllSections() {
    List<Section> sections = sectionService.getAllSections();
    return ResponseEntity.ok(sections);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteSection(@PathVariable Long id) {
    boolean deleted = sectionService.deleteSection(id);
    if (deleted) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity
        .badRequest()
        .body("Section cannot be deleted as it contains products.");
    }
  }
}
