package in.bushansirgur.restapi.service.impl;

import in.bushansirgur.restapi.dto.ExpenseDTO;
import in.bushansirgur.restapi.entity.ExpenseEntity;
import in.bushansirgur.restapi.repository.ExpenseRepository;
import in.bushansirgur.restapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * Mapper method to convert expense entity to expense DTO
     * @param expenseEntity
     * @return ExpenseDTO
     * **/
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity)
    {
        return modelMapper.map(expenseEntity,ExpenseDTO.class);
    }
}
