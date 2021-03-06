package com.example.tecnichalaprobar.service;

import com.example.tecnichalaprobar.util.Utilitarios;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import java.io.StringWriter;

@Component
public class EmailSender {

    private final JavaMailSender mailSender;
    private final VelocityEngine velocityEngine;

    public EmailSender(JavaMailSender mailSender, VelocityEngine velocityEngine) {
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
    }

    public void sendEmailUsingVelocityTemplate(final String subject, final String message,
                                               final String fromEmailAddress, final String toEmailAddress, String solicitud, String usuario) {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message1 = new MimeMessageHelper(mimeMessage);
            message1.setTo(toEmailAddress);
            message1.setFrom(new InternetAddress(fromEmailAddress));

            VelocityContext velocityContext = new VelocityContext();

            velocityContext.put("idsolicitud", solicitud);
            velocityContext.put("usuario", Utilitarios.encriptar(usuario));
            velocityContext.put("solicitud", Utilitarios.encriptar(solicitud));

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
