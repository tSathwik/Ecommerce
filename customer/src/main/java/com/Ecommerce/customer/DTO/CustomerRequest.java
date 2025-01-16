package com.Ecommerce.customer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
}
