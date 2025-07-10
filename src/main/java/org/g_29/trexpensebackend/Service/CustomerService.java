package org.g_29.trexpensebackend.Service;

import org.g_29.trexpensebackend.Model.Customer;

public interface CustomerService {

    public Customer findByEmail(String email);
    public Customer createCustomer(Customer newCustomer);
}
