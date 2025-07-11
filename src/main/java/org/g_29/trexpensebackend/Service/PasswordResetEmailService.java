package org.g_29.trexpensebackend.Service;

public interface PasswordResetEmailService {
    void sendPasswordResetEmail(String email, String resetLink) throws Exception;
    void sendMail(String email) throws Exception;
    void validateTokenAndReset(String email, String token, String newPassword)throws Exception;
}
