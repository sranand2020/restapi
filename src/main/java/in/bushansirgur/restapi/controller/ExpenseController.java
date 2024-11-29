package in.bushansirgur.restapi.controller;

import in.bushansirgur.restapi.dto.ExpenseDTO;
import in.bushansirgur.restapi.io.ExpenseRequest;
import in.bushansirgur.restapi.io.ExpenseResponse;
import in.bushansirgur.restapi.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        log.info("API GET /expenses is called");
        //Call the service method

        List<ExpenseDTO> list = expenseService.getAllExpenses();
        log.info("Printing the data from service {}",list);

        //Convert the ExpenseDTO to ExpenseResponse
        List<ExpenseResponse> response = list.stream().map(expenseDTO ->mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
        //Return the List/response
        return response;

    }

    /**
     * It will fetch the single expense from database.
     * @param expenseId
     * @return ExpenseResponse
     * */
    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable String expenseId)
    {
        log.info("API GET /expenses/{} is called",expenseId);
        ExpenseDTO expenseDTO = expenseService.getExpenseByexpenseId(expenseId);
        log.info("Printing the expense details {}",expenseDTO);
        return mapToExpenseResponse(expenseDTO);

    }

    /**
     * It will delete the single expense from database.
     * @param expenseId
     * @return void
     * */
    @ResponseStatus (HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpenseByExpenseId(@PathVariable String expenseId)
    {
        log.info("API DELETE /expenses/{} is called",expenseId);
        //return "Deleting the expense by id"+expenseId;
        expenseService.deleteExpenseByExpenseId(expenseId);
    }

    /**
     * It will save the expense details to the  database.
     * @param expenseRequest
     * @return ExpenseResponse
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest)
    {
         log.info("API POST /expenses called {}",expenseRequest);
         ExpenseDTO expenseDTO = mapToExpenseDTO(expenseRequest);
         expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
         log.info("Printing the expense DTO {}",expenseDTO);
         return mapToExpenseResponse(expenseDTO);
         //return expenseRequest;
    }

    /**
     * It will update the expense details to the  database.
     * @param updateRequest
     * @param expenseId
     * @return ExpenseResponse
     * */
    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest updateRequest,@PathVariable String expenseId){
        log.info("API PUT /expenses/{} request body {}",expenseId,updateRequest);
        ExpenseDTO updatedExpenseDTO = mapToExpenseDTO(updateRequest);
        //ToDO : Call the service method to update the details
        updatedExpenseDTO = expenseService.updateExpenseDetails(updatedExpenseDTO,expenseId);
        log.info("Printing the updated expense DTO details {}",updatedExpenseDTO);
        return mapToExpenseResponse(updatedExpenseDTO);
    }

    /**
     * Mapper method to map expenseRequest to expenseDTO
     * @param expenseRequest
     * @return ExpenseDTO
     * */
    private ExpenseDTO mapToExpenseDTO(ExpenseRequest expenseRequest) {
        return modelMapper.map(expenseRequest, ExpenseDTO.class);
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
