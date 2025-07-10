package org.g_29.trexpensebackend.Controller;

import org.g_29.trexpensebackend.Config.CustomUserDetailsImpl;
import org.g_29.trexpensebackend.Config.JwtProvider;
import org.g_29.trexpensebackend.DTO.*;
import org.g_29.trexpensebackend.Model.Customer;
import org.g_29.trexpensebackend.Service.CustomerServiceImpl;
import org.g_29.trexpensebackend.Service.SendEmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final CustomerServiceImpl customerServiceImpl;

    private final PasswordEncoder passwordEncoder;

    private final CustomUserDetailsImpl  customUserDetails;

    @Autowired
    private SendEmailServiceImpl sendEmailServiceImpl;

    public AuthController(CustomerServiceImpl customerServiceImpl, PasswordEncoder passwordEncoder, CustomUserDetailsImpl customUserDetails) {
        this.customerServiceImpl = customerServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetails = customUserDetails;
    }


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

        Authentication authentication=new UsernamePasswordAuthenticationToken(createdNewCustomer.getEmail(), createdNewCustomer.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= JwtProvider.generateToken(authentication);

        CreateCustomerResponseDTO  createCustomerResponseDTO=new CreateCustomerResponseDTO();
        createCustomerResponseDTO.setEmail(createCustomerDTO.getEmail());
        createCustomerResponseDTO.setMessage("User Created Successfully");
        createCustomerResponseDTO.setJwt(jwt);
        createCustomerResponseDTO.setStatusCode(HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.OK).body(createCustomerResponseDTO);



    }


    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequestDTO  loginRequestDTO) {

        Customer customer=customerServiceImpl.findByEmail(loginRequestDTO.getEmail());
        if(customer==null){
            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO();
            errorResponseDTO.setErrorMessage("User not found with this email ID");
            errorResponseDTO.setStatusCode(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(),customer.getPassword())){
            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO();
            errorResponseDTO.setErrorMessage("Invalid Password");
            errorResponseDTO.setStatusCode(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDTO);
        }

        String email=loginRequestDTO.getEmail();
        String password=loginRequestDTO.getPassword();

        Authentication authentication=authenticate(email,password);
        System.out.println(authentication.getPrincipal().toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=JwtProvider.generateToken(authentication);
        LoginResponseDTO loginResponseDTO=new LoginResponseDTO();
        loginResponseDTO.setEmail(email);
        loginResponseDTO.setJwt(jwt);
        loginResponseDTO.setMessage("User Logged in Successfully");
        loginResponseDTO.setStatusCode(HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);


    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails=customUserDetails.loadUserByUsername(email);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

    @GetMapping("/getUser")
    public ResponseEntity<?>getUserDetails(@RequestHeader("Authorization") String token){

        Customer customer=customerServiceImpl.getUserProfileByJWT(token);

        if(customer==null){
            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO();
            errorResponseDTO.setErrorMessage("User Not Found");
            errorResponseDTO.setStatusCode(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        UserDetailsResponseDTO userDetailsResponseDTO=new UserDetailsResponseDTO();
        userDetailsResponseDTO.setEmail(customer.getEmail());
        userDetailsResponseDTO.setName(customer.getName());

        return ResponseEntity.status(HttpStatus.OK).body(userDetailsResponseDTO);

    }

    @PostMapping("/activateAccount")
    public ResponseEntity<?>activateAccount(@RequestHeader("Authorization") String token, @RequestParam String email) throws Exception {

        System.out.println(email);


        Customer customer=customerServiceImpl.getUserProfileByJWT(token);
        if(customer==null){
            ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO();
            errorResponseDTO.setErrorMessage("User Not Found In this Email ID");
            errorResponseDTO.setStatusCode(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
        }

        sendEmailServiceImpl.sendEmailWithToken(email);

        return ResponseEntity.status(HttpStatus.OK).body("User Activated Successfully");

    }






}
