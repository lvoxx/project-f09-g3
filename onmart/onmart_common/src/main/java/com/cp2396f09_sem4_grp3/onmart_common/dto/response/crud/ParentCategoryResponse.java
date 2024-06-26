package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ParentCategoryResponse {
    private Long id;
    
    private String name;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("child_category")
    private List<ChildCategoryResponse> childCategory;
}
