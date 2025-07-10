package org.g_29.trexpensebackend.Repository;

import org.g_29.trexpensebackend.Model.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepo extends JpaRepository<AccountStatus,Long> {

    AccountStatus findByEmailAndToken(String email, String token);
    AccountStatus findByEmail(String email);
    AccountStatus findByToken(String token);
}
