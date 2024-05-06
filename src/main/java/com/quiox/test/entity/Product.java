package com.quiox.test.entity;

import javax.persistence.*;
import com.quiox.test.model.PackagingType;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", indexes = {
    @Index(name = "idx_product_name", columnList = "color"),
    @Index(name = "idx_product_price", columnList = "price"),
    @Index(name = "idx_product_lot", columnList = "lot"),
    @Index(name = "idx_product_is_fragile", columnList = "isFragile"),
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="product_sequence")
    @SequenceGenerator(name="product_sequence", sequenceName="product_sequence", allocationSize=100)
    private Long id;

    @Column
    private String name;

    @Column
    private double size;

    @Column
    private String color;

    @Column
    private double price;

    @Column
    private boolean isFragile;

    @Enumerated(EnumType.STRING)
    @Column
    private PackagingType packagingType;

    @Column
    private String lot;

    @Column
    private Long quantity;
    
}