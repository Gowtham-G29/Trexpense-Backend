package org.g_29.trexpensebackend.Service;

import org.g_29.trexpensebackend.Model.AccountStatus;
import org.g_29.trexpensebackend.Repository.AccountStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SendEmailServiceImpl implements SendEmailService{

    @Autowired
    private AccountStatusRepo accountStatusRepo;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Override
    public void sendEmailWithToken(String email) throws Exception{

        String invitationToken= UUID.randomUUID().toString();

        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setEmail(email);
        accountStatus.setToken(invitationToken);
        accountStatus.setStatus(false);

        accountStatusRepo.save(accountStatus);

        String ActivationLink="http://localhost:8080/activate_account?token="+invitationToken;
        emailServiceImpl.sendEmailWithToken(email,ActivationLink);

    }

    @Override
    public AccountStatus acceptInvitation(String email, String token) throws Exception {

        AccountStatus accountStatus=accountStatusRepo.findByEmailAndToken(email,token);

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

    }

}
