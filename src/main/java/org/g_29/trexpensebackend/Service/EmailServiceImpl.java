package org.g_29.trexpensebackend.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithToken(String email, String activationLink) throws Exception {
         MimeMessage mimeMessage = javaMailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");

        String subject = "Thank You for Visiting Trexpense!";
        String text = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <meta charset='UTF-8'>" +
                "  <style>" +
                "    .container {" +
                "      max-width: 600px;" +
                "      margin: auto;" +
                "      font-family: Arial, sans-serif;" +
                "      border: 1px solid #ddd;" +
                "      padding: 20px;" +
                "      background-color: #f9f9f9;" +
                "      border-radius: 10px;" +
                "    }" +
                "    .button {" +
                "      display: inline-block;" +
                "      padding: 12px 24px;" +
                "      margin-top: 20px;" +
                "      background-color: #4CAF50;" +
                "      color: white;" +
                "      text-decoration: none;" +
                "      border-radius: 5px;" +
                "      font-weight: bold;" +
                "    }" +
                "    .footer {" +
                "      margin-top: 30px;" +
                "      font-size: 12px;" +
                "      color: #777;" +
                "    }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class='container'>" +
                "    <h2>Welcome to <span style='color:#4CAF50;'>Trexpense</span>!</h2>" +
                "    <p>Thank you for signing up. You're almost ready to start exploring your travel and expense journey with Trexpense.</p>" +
                "    <p>Please click the button below to activate your account:</p>" +
                "    <a href='" + activationLink + "' class='button'>Activate Account</a>" +
                "    <p>If the button doesn't work, you can also copy and paste the following link into your browser:</p>" +
                "    <p><a href='" + activationLink + "'>" + activationLink + "</a></p>" +
                "    <div class='footer'>" +
                "      <p>&copy; 2025 Trexpense Inc. All rights reserved.</p>" +
                "    </div>" +
                "  </div>" +
                "</body>" +
                "</html>";

         helper.setSubject(subject);
         helper.setText(text,true);
         helper.setTo(email);
         helper.setFrom("gowthamguruvayurappan29@gmail.com");


        try{
             javaMailSender.send(mimeMessage);

         }catch (Exception e){
             throw new MailSendException("Failed to send email");
         }
    }
}
