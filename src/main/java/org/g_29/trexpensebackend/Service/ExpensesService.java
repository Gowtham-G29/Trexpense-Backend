package org.g_29.trexpensebackend.Service;

import org.g_29.trexpensebackend.Model.Expenses;

import java.util.List;

public interface ExpensesService {

    List<Expenses> getExpenses(Long userId)throws Exception;
    void deleteExpenses(Long expenseId)throws Exception;
}
