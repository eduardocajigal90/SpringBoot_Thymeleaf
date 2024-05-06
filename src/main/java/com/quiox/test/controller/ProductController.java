package com.quiox.test.controller;

import com.quiox.test.entity.Product;
import com.quiox.test.projection.ProductRequestDto;
import com.quiox.test.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  @Autowired
  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Product> createProduct(
    @RequestBody ProductRequestDto product
  ) {
    Product createdProduct = productService.saveProduct(product);
    return ResponseEntity.ok(createdProduct);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    return productService
      .getProductById(id)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/filter")
  public ResponseEntity<List<Product>> filterProducts(
    @RequestParam(required = false) Long sectionId,
    @RequestParam(required = false) String lot,
    @RequestParam(required = false) Boolean isFragile,
    @RequestParam(required = false) String color,
    @RequestParam(required = false) Double minPrice,
    @RequestParam(required = false) Double maxPrice,
    @RequestParam(required = false) String packagingType
  ) {
    List<Product> products = productService.filterProducts(
      sectionId,
      lot,
      isFragile,
      color,
      minPrice,
      maxPrice,
      packagingType
    );
    return ResponseEntity.ok(products);
  }

  @GetMapping("/report")
  public ResponseEntity<List<Object[]>> getProductReport() {
    List<Object[]> report = productService.getProductCountsBySection();
    return ResponseEntity.ok(report);
  }

  @GetMapping("/add-quantity")
  public ResponseEntity<Product> addProductCount(
    @RequestParam(required = true) Long productId,
    @RequestParam(required = true) Long sectionId,
    @RequestParam(required = true) Long quantity
  ) {
    Product product = productService.addProductToSection(
      productId,
      sectionId,
      0
    );
    return ResponseEntity.ok(product);
  }
}
