package org.g_29.trexpensebackend.Repository;

import jakarta.transaction.Transactional;
import org.g_29.trexpensebackend.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    @Transactional
    Customer findByEmail(String email);

}
