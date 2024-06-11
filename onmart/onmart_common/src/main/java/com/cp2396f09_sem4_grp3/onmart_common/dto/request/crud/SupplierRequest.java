package com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class SupplierRequest {

    @JsonProperty("supplier_name")
    private String supplierName;
    @JsonProperty("contact_person")
    private String contactPerson;
    @JsonProperty("contact_number")
    private String contactNumber;
    private String email;

    private AddressRequest address;

}
