package com.helloguyzs.syfetask.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.helloguyzs.syfetask.enums.CategoryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoriesResponse {


    private String name ;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @JsonProperty("isCustom")
    private boolean isCustom;

}
