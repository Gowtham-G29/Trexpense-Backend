package org.g_29.trexpensebackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsMailRequestDTO {
    private String message;
    private String email;
}
