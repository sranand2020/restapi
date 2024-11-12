package in.bushansirgur.restapi.repository;

import in.bushansirgur.restapi.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA repository for exxpense resource
 * @author Anand Raj S
 * **/
public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {

    /**
     * It will find the single expense details from the database
     * @param expenseId
     * @return Optional
     * **/
    Optional<ExpenseEntity> findByExpenseId(String expenseId);
}
