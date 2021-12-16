package com.example.tecnichalaprobar.controller;

import com.example.tecnichalaprobar.service.EmailSender;
import com.example.tecnichalaprobar.util.Utilitarios;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MailController {

    private static final Logger LOG = LoggerFactory.getLogger(MailController.class);
    private final EmailSender emailSender;
    private Utilitarios utilitarios;

    public MailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("/aprobarsolicitud")
    public ResponseEntity<Void> aprobarSolicitud(@RequestParam Map<String,String> allParams) {
        try {
            String usuario = Utilitarios.desencriptar(allParams.get("usuario"));
            String solicitud = Utilitarios.desencriptar(allParams.get("solicitud"));
            LOG.info("usuario: " + usuario + " solicitud: " + solicitud);
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/encriptacion")
    public ResponseEntity<Void> encriptar(@RequestParam Map<String,String> allParams) {
        try {
            String usuario = Utilitarios.encriptar(allParams.get("usuario"), "Dz95mH1tOySrMlGLhUaAAA==");
            String solicitud = Utilitarios.encriptar(allParams.get("solicitud"), "Dz95mH1tOySrMlGLhUaAAA==");
            LOG.info("usuario: " + usuario + " solicitud: " + solicitud);
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail() {
        emailSender.sendEmailUsingVelocityTemplate("Email using Velocity Template Library",
                "", "joseespino.ti@gmail.com", "joseespino.ti@gmail.com");
        return ResponseEntity.accepted().build();
    }
}
