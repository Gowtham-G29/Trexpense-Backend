package org.g_29.trexpensebackend.Service;

import org.g_29.trexpensebackend.Config.JwtProvider;
import org.g_29.trexpensebackend.Model.Customer;
import org.g_29.trexpensebackend.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements  CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public Customer findByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    @Override
    public Customer createCustomer(Customer newCustomer) {
        return customerRepo.save(newCustomer);
    }

    @Override
    public Customer getUserProfileByJWT(String jwt) {

        String email= JwtProvider.getEmailFromToken(jwt);
        System.out.println("email:"+email);

        return customerRepo.findByEmail(email);
    }
}
