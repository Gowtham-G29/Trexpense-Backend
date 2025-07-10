package org.g_29.trexpensebackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerResponseDTO {

    private String email;
    private String message;
    private HttpStatus statusCode;
    private String jwt;
}
