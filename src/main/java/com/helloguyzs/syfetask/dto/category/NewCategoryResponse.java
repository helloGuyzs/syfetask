package com.helloguyzs.syfetask.dto.category;

import com.helloguyzs.syfetask.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewCategoryResponse {


    private String name;

    private CategoryType type;

    private boolean isCustom;
}
