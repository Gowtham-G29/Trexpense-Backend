package org.g_29.trexpensebackend.Controller;

import org.g_29.trexpensebackend.Model.ContactUsMailRequestDTO;
import org.g_29.trexpensebackend.Service.ContactUsEmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactUsController {

    @Autowired
    private ContactUsEmailServiceImpl contactUsEmailServiceImpl;

    @PostMapping("/contactUs")
    public ResponseEntity<?>sendContactUsQueryEmail(@RequestBody ContactUsMailRequestDTO contactUsMailRequestDTO) throws Exception{

        contactUsEmailServiceImpl.sendEmail(contactUsMailRequestDTO.getEmail(),contactUsMailRequestDTO.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body("Mail Sent to Support team! ");

    }

}
