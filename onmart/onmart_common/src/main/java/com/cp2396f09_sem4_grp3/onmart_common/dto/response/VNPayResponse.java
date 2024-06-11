package com.cp2396f09_sem4_grp3.onmart_common.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VNPayResponse {
    private String code;
    private String message;
    private String paymentUrl;
}
