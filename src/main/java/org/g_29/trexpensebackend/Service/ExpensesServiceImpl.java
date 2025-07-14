package org.g_29.trexpensebackend.Service;

import jakarta.transaction.Transactional;
import org.g_29.trexpensebackend.Model.Expenses;
import org.g_29.trexpensebackend.Repository.ExpensesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesServiceImpl implements ExpensesService {


    private final ExpensesRepo expensesRepo;

    public ExpensesServiceImpl(ExpensesRepo expensesRepo) {
        this.expensesRepo = expensesRepo;
    }

    @Override
    @Transactional
    public List<Expenses> getExpenses(Long userId)throws Exception {

        return expensesRepo.findExpensesByCustomer_Id(userId);

    }

}
