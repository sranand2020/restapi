package in.bushansirgur.restapi.service;

import in.bushansirgur.restapi.dto.ExpenseDTO;

import java.util.List;

/**
 * Service interface for expense module
 * @author Anand Raj S
 * **/
public interface ExpenseService {

/**
 * It will fetch the expenses from the database
 * @return list
 * **/
    List<ExpenseDTO> getAllExpenses();

    /**
     * It will fetch the single expense details from the database
     * @param expenseId
     * @return ExpenseDTO
     * **/
    ExpenseDTO getExpenseByexpenseId(String expenseId);
}
