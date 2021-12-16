package com.example.tecnichalaprobar.controller;

import com.example.tecnichalaprobar.model.GestorAprobador;
import com.example.tecnichalaprobar.repository.GestorAprobadorRepository;
import com.example.tecnichalaprobar.service.EmailSender;
import com.example.tecnichalaprobar.util.Utilitarios;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
public class MailController {

    private static final Logger LOG = LoggerFactory.getLogger(MailController.class);
    private final EmailSender emailSender;
    private final GestorAprobadorRepository gestorAprobadorRepository;

    public MailController(EmailSender emailSender, GestorAprobadorRepository gestorAprobadorRepository) {
        this.emailSender = emailSender;
        this.gestorAprobadorRepository = gestorAprobadorRepository;
    }

    @GetMapping("/aprobarsolicitud")
    public ResponseEntity<Void> aprobarSolicitud(@RequestParam Map<String,String> allParams) {
        try {
            String usuario = Utilitarios.desencriptar(allParams.get("usuario"));
            String solicitud = Utilitarios.desencriptar(allParams.get("solicitud"));
            boolean aprobado = Boolean.parseBoolean(allParams.get("aprobado"));
            GestorAprobador datos = gestorAprobadorRepository.findGestorAprobadorByUsuarioidAndSolicitudid(
                    Integer.parseInt(usuario),
                    Integer.parseInt(solicitud)
            );
            if(!ObjectUtils.isEmpty(datos)) {
                if(ObjectUtils.isEmpty(datos.getEmitioaccion())) {
                    gestorAprobadorRepository.save(GestorAprobador
                            .builder()
                                    .id(datos.getId())
                                    .usuarioid(datos.getUsuarioid())
                                    .solicitudid(datos.getSolicitudid())
                                    .emitioaccion(1)
                                    .aprobado(aprobado ? 1 : 0)
                            .build());
                } else {
                    URI yahoo = new URI("http://www.yahoo.com");
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(yahoo);
                    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
                }
            }
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
    public ResponseEntity<Void> sendEmail() {
        emailSender.sendEmailUsingVelocityTemplate("Email using Velocity Template Library",
                "", "joseespino.ti@gmail.com", "joseespino.ti@gmail.com");
        return ResponseEntity.accepted().build();
    }
}
