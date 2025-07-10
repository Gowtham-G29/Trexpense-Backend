package org.g_29.trexpensebackend.Controller;

import org.g_29.trexpensebackend.DTO.CreateCustomerRequestDTO;
import org.g_29.trexpensebackend.DTO.CreateCustomerResponseDTO;
import org.g_29.trexpensebackend.DTO.ErrorResponseDTO;
import org.g_29.trexpensebackend.Config.JwtProvider;
import org.g_29.trexpensebackend.Model.Customer;
import org.g_29.trexpensebackend.Service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?>createCustomer(@RequestBody CreateCustomerRequestDTO createCustomerDTO) {

        Customer isCustomerExists=customerServiceImpl.findByEmail(createCustomerDTO.getEmail());

        if(isCustomerExists!=null){

            if(isCustomerExists.getName().equals(createCustomerDTO.getName())){
                ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
                errorResponseDTO.setErrorMessage("Customer already exists with this Username");
                errorResponseDTO.setStatusCode(HttpStatus.CONFLICT);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
            }
            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO();
            errorResponseDTO.setErrorMessage("Customer already exists with this email ID");
            errorResponseDTO.setStatusCode(HttpStatus.CONFLICT);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
        }

        Customer newCustomer=new Customer();
        newCustomer.setName(createCustomerDTO.getName());
        newCustomer.setEmail(createCustomerDTO.getEmail());
        newCustomer.setPassword(passwordEncoder.encode(createCustomerDTO.getPassword()));

        Customer createdNewCustomer=customerServiceImpl.createCustomer(newCustomer);

        Authentication authentication=new UsernamePasswordAuthenticationToken(createCustomerDTO.getEmail(), createCustomerDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= JwtProvider.generateToken(authentication);

        CreateCustomerResponseDTO  createCustomerResponseDTO=new CreateCustomerResponseDTO();
        createCustomerResponseDTO.setEmail(createCustomerDTO.getEmail());
        createCustomerResponseDTO.setMessage("User Created Successfully");
        createCustomerResponseDTO.setJwt(jwt);
        createCustomerResponseDTO.setStatusCode(HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.OK).body(createCustomerResponseDTO);



    }


    public ResponseEntity<?>login(@RequestBody )

}
