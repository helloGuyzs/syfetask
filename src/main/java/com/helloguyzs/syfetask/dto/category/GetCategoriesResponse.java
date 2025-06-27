package com.helloguyzs.syfetask.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.helloguyzs.syfetask.enums.CategoryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoriesResponse {

    private List<NewCategoryResponse> categories ;

}
