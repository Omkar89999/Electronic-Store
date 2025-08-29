package com.electronic.store.dtos;

import com.electronic.store.validate.ImageNameValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "title is reqired !!")
    @Size(min = 4, message = "title must be 4 character !!")
    private String title;

    @NotBlank(message = "description required !!")
    private String description;

    @ImageNameValid
    private String coverImage;

}
