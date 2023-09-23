package com.vaistramanagement.vaistramanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entity")
public class EntityType
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "entity_id")
    private int entityId;


    @Column(name = "entity_name")
    private String shortName;

    @Column(name = "entity_type")
    private String entityType;

}
