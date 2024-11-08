package in.bushansirgur.restapi.controller;

import in.bushansirgur.restapi.dto.ExpenseDTO;
import in.bushansirgur.restapi.io.ExpenseResponse;
import in.bushansirgur.restapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is controller class for Expense module
 * @author Anand Raj S
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the expenses from database.
     * @return list
     * */
    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses()
    {
        log.info("API GET /responses is called");
        //Call the service method

        List<ExpenseDTO> list = expenseService.getAllExpenses();
        log.info("Printing the data from service {}",list);

        //Convert the ExpenseDTO to ExpenseResponse
        List<ExpenseResponse> response = list.stream().map(expenseDTO ->mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
        //Return the List/response
        return response;

    }
/**
 * Mapper method for converting Expense DTO object to Expense response object
 * @param expenseDTO
 * @return ExpenseResponse
 * */
    private ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO,ExpenseResponse.class);
    }
}
