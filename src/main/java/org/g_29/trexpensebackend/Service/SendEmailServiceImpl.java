package org.g_29.trexpensebackend.Service;

import org.g_29.trexpensebackend.Model.AccountStatus;
import org.g_29.trexpensebackend.Model.Customer;
import org.g_29.trexpensebackend.Repository.AccountStatusRepo;
import org.g_29.trexpensebackend.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SendEmailServiceImpl implements SendEmailService{

    @Autowired
    private AccountStatusRepo accountStatusRepo;

    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private CustomerServiceImpl customerServiceImpl;
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public void sendEmailWithToken(String email) throws Exception{


        String invitationToken= UUID.randomUUID().toString();

        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setEmail(email);
        accountStatus.setToken(invitationToken);
        accountStatus.setStatus(false);

        Customer customer=customerServiceImpl.findByEmail(email);
        customer.setAccountStatus(accountStatus);
        customerRepo.save(customer);

        String ActivationLink="http://localhost:5173/activatePage?token="+invitationToken;
        emailServiceImpl.sendEmailWithToken(email,ActivationLink);

    }

    @Override
    public AccountStatus acceptActivation(String email) throws Exception {

        AccountStatus accountStatus=accountStatusRepo.findByEmail(email);

        if(accountStatus==null){
            throw new Exception("Invalid email or token");
        }

        return accountStatus;

    }

    @Override
    public String getTokenByUserEmail(String email) throws Exception {
        AccountStatus accountStatus=accountStatusRepo.findByEmail(email);
        return accountStatus.getToken();
    }

    @Override
    public void deleteToken(String token) throws Exception {

        AccountStatus accountStatus=accountStatusRepo.findByToken(token);
        accountStatus.setToken(null);
        accountStatusRepo.save(accountStatus);

    }

}
