package com.quiox.test.entity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DynamicUpdate
@Table(name = "section")
public class Section implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="section_sequence")
    @SequenceGenerator(name="section_sequence", sequenceName="section_sequence", allocationSize=100)
    private Long id;

    @Column
    private Double sizeInSquareMeters;

    @OneToMany
    @JoinColumn(name = "section_id")
    private List<Product> products = new ArrayList<>();
    
}

