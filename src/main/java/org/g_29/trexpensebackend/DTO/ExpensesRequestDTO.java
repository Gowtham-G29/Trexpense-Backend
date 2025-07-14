package org.g_29.trexpensebackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesRequestDTO {

    private double amount;
    private String purpose;
    private String note;
    private String latitude;
    private String longitude;
    private String address;

}
