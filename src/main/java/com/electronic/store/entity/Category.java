package com.electronic.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name = "category_title", length = 60, nullable = false)
    private String title;
    @Column(name = "category_desc", length = 500)
    private String description;

    private String coverImage;

}
