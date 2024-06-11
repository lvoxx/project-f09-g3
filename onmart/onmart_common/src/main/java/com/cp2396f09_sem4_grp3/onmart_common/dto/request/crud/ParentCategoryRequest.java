package com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud;

import org.springframework.web.multipart.MultipartFile;

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
public class ParentCategoryRequest {
    private String name;
    private MultipartFile image;
}