package com.example.tecnichalaprobar.controller;

import com.example.tecnichalaprobar.repository.UsuarioSolicitudRepository;
import com.example.tecnichalaprobar.service.AprobarService;
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
    private final AprobarService aprobarService;
    private final UsuarioSolicitudRepository usuarioSolicitudRepository;

    public MailController(EmailSender emailSender, AprobarService aprobarService, UsuarioSolicitudRepository usuarioSolicitudRepository) {
        this.emailSender = emailSender;
        this.aprobarService = aprobarService;
        this.usuarioSolicitudRepository = usuarioSolicitudRepository;
    }

    @GetMapping("/aprobarsolicitud")
    public ResponseEntity<Object> aprobarSolicitud(@RequestParam Map<String,String> allParams) {
        try {
            return ResponseEntity.ok().body(aprobarService.aprobarSolicitud(allParams));
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/encriptacion")
    public ResponseEntity<Void> encriptar(@RequestParam Map<String,String> allParams) {
        try {
            String usuario = Utilitarios.encriptar(allParams.get("usuario"));
            String solicitud = Utilitarios.encriptar(allParams.get("solicitud"));
            LOG.info("usuario: " + usuario + " solicitud: " + solicitud);
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail(@RequestParam Map<String,String> allParams) {
        try {
            String solicitud = allParams.get("solicitud");
            usuarioSolicitudRepository.findUsuarioSolicitudBySolicitudid(Integer.parseInt(solicitud)).forEach(x ->
                            emailSender.sendEmailUsingVelocityTemplate("Email Aprobador",
                                    "", "joseespino.ti@gmail.com",
                                    x.getEmail(), solicitud, x.getUsuarioid().toString())
            );
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
        return ResponseEntity.accepted().build();
    }
}
