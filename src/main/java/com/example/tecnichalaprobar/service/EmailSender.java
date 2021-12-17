package com.example.tecnichalaprobar.service;

import com.example.tecnichalaprobar.util.Utilitarios;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Map;

@Component
public class EmailSender {

    private final JavaMailSender mailSender;
    private final VelocityEngine velocityEngine;

    public EmailSender(JavaMailSender mailSender, VelocityEngine velocityEngine) {
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
    }

    public void sendEmailUsingVelocityTemplate(final String subject, final String message,
                                               final String fromEmailAddress, final String toEmailAddress, Map<String,String> params) {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message1 = new MimeMessageHelper(mimeMessage);
            message1.setTo(toEmailAddress);
            message1.setFrom(new InternetAddress(fromEmailAddress));

            VelocityContext velocityContext = new VelocityContext();

            String usuario = Utilitarios.encriptar(params.get("usuario"));
            String solicitud = Utilitarios.encriptar(params.get("solicitud"));
            boolean aprobado = Boolean.parseBoolean(params.get("aprobado"));

            velocityContext.put("idsolicitud", params.get("solicitud"));
            velocityContext.put("usuario", usuario);
            velocityContext.put("solicitud", solicitud);
            velocityContext.put("aprobado", aprobado);

            StringWriter stringWriter = new StringWriter();

            velocityEngine.mergeTemplate("templates/email.vm", "UTF-8", velocityContext, stringWriter);

            message1.setSubject(subject);
            message1.setText(stringWriter.toString(), true);
        };

        try {
            mailSender.send(preparator);

            System.out.println("Email sending complete.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
