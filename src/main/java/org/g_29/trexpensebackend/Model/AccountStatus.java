package org.g_29.trexpensebackend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String token;

    private Boolean status;

    @OneToOne(mappedBy = "accountStatus",cascade = CascadeType.ALL,orphanRemoval = true)
    private Customer customer;

}
