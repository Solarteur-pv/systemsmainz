package dev.yukado.systemsmainz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "own_categories")
public class OwnCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
}

