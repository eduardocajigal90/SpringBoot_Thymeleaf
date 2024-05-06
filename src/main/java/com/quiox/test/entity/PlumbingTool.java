package com.quiox.test.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@PrimaryKeyJoinColumn(referencedColumnName="id")
public class PlumbingTool extends Product {

    @Column
    private String measurements;
 
}