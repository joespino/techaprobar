package com.example.tecnichalaprobar.controller;

import com.example.tecnichalaprobar.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class MailController {

    private static final Logger LOG = LoggerFactory.getLogger(MailController.class);
    private final EmailSender emailSender;

    public MailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("/")
    public ResponseEntity<Void> saludo() {
        LOG.info("Hola");
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/aprobarsolicitud")
    public ResponseEntity<Void> aprobarSolicitud(@RequestParam String usuario,
                                                 @RequestParam String idSolicitud,
                                                 @RequestParam boolean aprobar) throws URISyntaxException {
        LOG.info("Informacion de prueba");
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail() {
        emailSender.sendEmailUsingVelocityTemplate("Email using Velocity Template Library",
                "", "joseespino.ti@gmail.com", "joseespino.ti@gmail.com");
        return ResponseEntity.accepted().build();
    }
}
