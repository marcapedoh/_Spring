package com.gestiondestock.spring.Controller;

import com.gestiondestock.spring.Auth.EmailRequest;
import com.gestiondestock.spring.Email.EmailResponse;
import com.gestiondestock.spring.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gestiondestock.spring.Constants.Utils.APP_ROOT;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(APP_ROOT+"/email")
public class EmailController {
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        String toList = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        String text = emailRequest.getText();
        /*for (String to : toList) {*/
            emailService.sendEmail(toList, subject, text);
       /* }*/
    }
    @GetMapping("/receive")
    public List<EmailResponse> receiveEmails() {
       return  emailService.receiveEmails();
    }
}
