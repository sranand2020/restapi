package in.bushansirgur.restapi.service.impl;

import in.bushansirgur.restapi.dto.ExpenseDTO;
import in.bushansirgur.restapi.entity.ExpenseEntity;
import in.bushansirgur.restapi.exceptions.ResourceNotFoundException;
import in.bushansirgur.restapi.repository.ExpenseRepository;
import in.bushansirgur.restapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Service implementation for expense module
 * @author Anand Raj S
 * **/
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;


    /**
     * It will fetch the expenses from the database
     * @return list
     * **/
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        //Call the repository method
    List<ExpenseEntity>  list =  expenseRepository.findAll();
    log.info("Printing the data from the repository {}",list);
        //Convert the Entity object to DTO object
       List<ExpenseDTO> listOfExpenses =  list.stream().map(expenseEntity ->mapToExpenseDTO(expenseEntity)).collect(Collectors.toList());


        //Return the list

        return listOfExpenses;


    }

    /**
     * It will fetch the single expense details from the database
     * @param expenseId
     * @return ExpenseDTO
     * **/
    @Override
    public ExpenseDTO getExpenseByexpenseId(String expenseId) {

        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        log.info("Printing the expense Entity details {}",expenseEntity);
        return mapToExpenseDTO(expenseEntity);
    }



    /**
     * It will delete the single expense details from the database
     * @param expenseId
     * @return void
     * **/
    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
         ExpenseEntity expenseEntity =  getExpenseEntity(expenseId);
         log.info("Printing the expense entity {}",expenseEntity);
         expenseRepository.delete(expenseEntity);
    }

    /**
     * It will save the expense details to the database
     * @param expenseDTO
     * @return ExpenseDTO
     * **/
    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        ExpenseEntity newExpenseEntity =maptoExpenseEntity(expenseDTO);
        newExpenseEntity.setExpenseId(UUID.randomUUID().toString());
        newExpenseEntity = expenseRepository.save(newExpenseEntity);
        log.info("Printing the new expense entity details {}",newExpenseEntity);
        return mapToExpenseDTO(newExpenseEntity);
    }

    /**
     * Mapper method to map values from expenseDTO to ExpenseEntity
     * @param expenseDTO
     * @return ExpenseEntity
     * **/
    private ExpenseEntity maptoExpenseEntity(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO,ExpenseEntity.class);
    }


    /**
     * Mapper method to convert expense entity to expense DTO
     * @param expenseEntity
     * @return ExpenseDTO
     * **/
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity)
    {
        return modelMapper.map(expenseEntity,ExpenseDTO.class);
    }

    /**
     * Fetch the expense based on the expenseId from the database
     * @param expenseId
     * @return ExpenseEntity
     * **/
    private ExpenseEntity getExpenseEntity(String expenseId) {
        return expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() ->new ResourceNotFoundException("Expense Not Found for the expense id "+ expenseId));

    }
}
