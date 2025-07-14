package org.g_29.trexpensebackend.Controller;

import org.g_29.trexpensebackend.DTO.ErrorResponseDTO;
import org.g_29.trexpensebackend.Model.Customer;
import org.g_29.trexpensebackend.Repository.CustomerRepo;
import org.g_29.trexpensebackend.Service.CustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;
    private final CustomerRepo customerRepo;

    public CustomerController(CustomerServiceImpl customerServiceImpl, CustomerRepo customerRepo) {
        this.customerServiceImpl = customerServiceImpl;
        this.customerRepo = customerRepo;
    }

    @PatchMapping("/updateProfileImage")
    public ResponseEntity<?>updateProfilePicture(@RequestHeader("Authorization") String jwt, @RequestParam MultipartFile profilePicture)throws Exception {

        Customer customer=customerServiceImpl.getUserProfileByJWT(jwt);
        if(customer==null){
            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO();
            errorResponseDTO.setErrorMessage("User Found");
            errorResponseDTO.setStatusCode(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDTO);
        }

          customer.setProfilePicture(profilePicture.getBytes());
          customerRepo.save(customer);
          return  ResponseEntity.status(HttpStatus.OK).body("Profile Picture Updated Successfully");


    }

    @GetMapping("/getUserProfile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
        Customer customer=customerServiceImpl.getUserProfileByJWT(jwt);
        if(customer==null){
            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO();
            errorResponseDTO.setErrorMessage("User Found");
            errorResponseDTO.setStatusCode(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}
