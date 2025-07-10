package org.g_29.trexpensebackend.Service;

import org.g_29.trexpensebackend.Model.AccountStatus;

public interface SendEmailService {

    void sendEmailWithToken(String email) throws Exception;

    AccountStatus acceptInvitation(String email, String token) throws Exception;

    String getTokenByUserEmail(String email) throws Exception;

    void deleteToken(String token) throws Exception;


}
