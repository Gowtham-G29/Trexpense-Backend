package org.g_29.trexpensebackend.Repository;

import org.g_29.trexpensebackend.Model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses, Long> {

    List<Expenses> findExpensesByCustomer_Id(Long userId);

}
