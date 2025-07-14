package org.g_29.trexpensebackend.Controller;

import org.g_29.trexpensebackend.DTO.ErrorResponseDTO;
import org.g_29.trexpensebackend.DTO.ExpensesRequestDTO;
import org.g_29.trexpensebackend.Model.Customer;
import org.g_29.trexpensebackend.Model.Expenses;
import org.g_29.trexpensebackend.Repository.ExpensesRepo;
import org.g_29.trexpensebackend.Service.CustomerServiceImpl;
import org.g_29.trexpensebackend.Service.ExpensesServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ExpensesController {

    private final CustomerServiceImpl customerServiceImpl;
    private final ExpensesRepo expensesRepo;
    private final ExpensesServiceImpl expensesServiceImpl;

    public ExpensesController(CustomerServiceImpl customerServiceImpl, ExpensesRepo expensesRepo, ExpensesServiceImpl expensesServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
        this.expensesRepo = expensesRepo;
        this.expensesServiceImpl = expensesServiceImpl;
    }

    @PostMapping("/saveExpenses")
    public ResponseEntity<?>saveExpenses(@RequestHeader("Authorization") String jwt,
                                        @ModelAttribute ExpensesRequestDTO expensesRequestDTO,
                                        @RequestParam MultipartFile image) throws Exception{

        Customer customer = customerServiceImpl.getUserProfileByJWT(jwt);

        if(customer == null){
            ErrorResponseDTO errorResponse = new ErrorResponseDTO();
            errorResponse.setErrorMessage("Invalid User");
            errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Expenses newExpenses = new Expenses();
        newExpenses.setAmount(expensesRequestDTO.getAmount());
        newExpenses.setPurpose(expensesRequestDTO.getPurpose());
        newExpenses.setNote(expensesRequestDTO.getNote());
        newExpenses.setAddress(expensesRequestDTO.getAddress());
        newExpenses.setLatitude(expensesRequestDTO.getLatitude());
        newExpenses.setLongitude(expensesRequestDTO.getLongitude());
        newExpenses.setImage(image.getBytes());

        newExpenses.setCustomer(customer);

        expensesRepo.save(newExpenses);
        return ResponseEntity.status(HttpStatus.OK).body(newExpenses);

    }

    @GetMapping("/getUserExpenses")
    public ResponseEntity<?>getExpenses(@RequestHeader("Authorization") String jwt) throws Exception{

        Customer customer = customerServiceImpl.getUserProfileByJWT(jwt);
        if(customer == null){
            ErrorResponseDTO errorResponse = new ErrorResponseDTO();
            errorResponse.setErrorMessage("Invalid User");
            errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        List<Expenses>expenses=expensesServiceImpl.getExpenses(customer.getId());

        if(expenses.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("No expenses found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(expenses);
    }
}
