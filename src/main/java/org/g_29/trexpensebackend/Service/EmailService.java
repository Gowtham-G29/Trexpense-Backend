package org.g_29.trexpensebackend.Service;

public interface EmailService {
    void sendEmailWithToken(String email, String activationLink) throws Exception;
}
