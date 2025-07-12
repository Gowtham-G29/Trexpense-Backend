package org.g_29.trexpensebackend.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ContactUsEmailServiceImpl implements ContactUsEmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String senderEmail, String message) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String subject = "📩 Query Request from Trexpense User";

        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\" />\n" +
                "  <title>New Contact Query</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      background-color: #f4f4f4;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "    .email-container {\n" +
                "      background-color: #ffffff;\n" +
                "      padding: 30px;\n" +
                "      border-radius: 8px;\n" +
                "      max-width: 600px;\n" +
                "      margin: 0 auto;\n" +
                "      box-shadow: 0 0 10px rgba(0,0,0,0.05);\n" +
                "    }\n" +
                "    .header {\n" +
                "      font-size: 22px;\n" +
                "      font-weight: bold;\n" +
                "      color: #333333;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "    .info { margin-bottom: 15px; }\n" +
                "    .label { font-weight: bold; color: #555555; }\n" +
                "    .value { color: #222222; }\n" +
                "    .footer {\n" +
                "      margin-top: 30px;\n" +
                "      font-size: 13px;\n" +
                "      color: #888888;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"email-container\">\n" +
                "    <div class=\"header\">📨 New Contact Query Received - Trexpense</div>\n" +
                "    <div class=\"info\">\n" +
                "      <div class=\"label\">Email:</div>\n" +
                "      <div class=\"value\">" + senderEmail + "</div>\n" +
                "    </div>\n" +
                "    <div class=\"info\">\n" +
                "      <div class=\"label\">Message:</div>\n" +
                "      <div class=\"value\">" + message + "</div>\n" +
                "    </div>\n" +
                "    <div class=\"footer\">\n" +
                "      This message was generated via the Contact Us form on the <strong>Trexpense</strong> website.\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";

        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setTo("gowtham.21ec@kct.ac.in");
        helper.setFrom("gowthamguruvayurappan29@gmail.com");

        try{
            mailSender.send(mimeMessage);
        }catch(Exception ignored){
            throw new  Exception("Unable to send email");
        }

    }


}
