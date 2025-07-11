package org.g_29.trexpensebackend.Service;

import jakarta.mail.internet.MimeMessage;
import org.g_29.trexpensebackend.Model.Customer;
import org.g_29.trexpensebackend.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetEmailServiceImpl implements PasswordResetEmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void sendPasswordResetEmail(String email, String resetLink) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        String subject = "Reset Password";
        String text =
                "<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "  <meta charset=\"UTF-8\" />" +
                        "  <title>Reset Your Trexpense Password</title>" +
                        "  <style>" +
                        "    body { background-color: #f9fafb; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; }" +
                        "    .container { background-color: #ffffff; max-width: 600px; margin: 40px auto; padding: 30px; border-radius: 10px; box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05); text-align: center; }" +
                        "    .header { color: #0f172a; font-size: 22px; font-weight: bold; margin-bottom: 10px; }" +
                        "    .highlight { color: #f97316; }" +
                        "    .button { display: inline-block; margin-top: 24px; padding: 12px 24px; font-size: 16px; background-color: #f97316; color: white; text-decoration: none; border-radius: 6px; }" +
                        "    .footer { margin-top: 40px; font-size: 12px; color: #64748b; }" +
                        "  </style>" +
                        "</head>" +
                        "<body>" +
                        "  <div class=\"container\">" +
                        "    <h2 class=\"header\">Reset Your <span class=\"highlight\">Trexpense</span> Password</h2>" +
                        "    <p>Hello,</p>" +
                        "    <p>We received a request to reset your Trexpense account password. To proceed, click the button below:</p>" +
                        "    <a href=\"" + resetLink + "\" class=\"button\">Reset Password</a>" +
                        "    <p style=\"margin-top: 20px;\">If you didn’t request this, you can safely ignore this email.</p>" +
                        "    <div class=\"footer\">" +
                        "      <p>&copy; Trexpense, 2025. All rights reserved.</p>" +
                        "    </div>" +
                        "  </div>" +
                        "</body>" +
                        "</html>";


        helper.setSubject(subject);
        helper.setText(text,true);
        helper.setTo(email);
        helper.setFrom("gowthamguruvayurappan29@gmail.com");

        try {
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Email could not be sent");
        }

    }

    @Override
    public void sendMail(String email) throws Exception{
        String secretToken= UUID.randomUUID().toString();

        Customer customer=customerRepo.findByEmail(email);
        customer.setPasswordResetToken(secretToken);
        customerRepo.save(customer);

        String resetLink="http://localhost:5173/resetPassword?token="+secretToken+"&email="+email;

        try {
            sendPasswordResetEmail(email,resetLink);

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Email could not be sent");
        }
    }

    @Override
    public void validateTokenAndReset(String email, String token, String newPassword)throws Exception{
        Customer customer=customerRepo.findByEmail(email);

        String originalToken=customer.getPasswordResetToken();

        if(originalToken.equals(token)){
            customer.setPasswordResetToken(null);
            customer.setPassword(passwordEncoder.encode(newPassword));
            customerRepo.save(customer);
        }else{
            throw new Exception("Invalid token");
        }
    }

}
