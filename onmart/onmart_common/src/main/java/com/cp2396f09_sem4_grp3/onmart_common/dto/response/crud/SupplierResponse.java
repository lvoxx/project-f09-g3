package com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud;

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
public class SupplierResponse {

    private Long id;
    @JsonProperty("supplier_name")
    private String supplierName;
    @JsonProperty("contact_person")
    private String contactPerson;
    @JsonProperty("contact_number")
    private String contactNumber;
    private String email;
    
    private AddressResponse address;

}
