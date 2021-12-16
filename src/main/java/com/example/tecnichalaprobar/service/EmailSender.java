package com.example.tecnichalaprobar.service;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
                                               final String fromEmailAddress, final String toEmailAddress) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(toEmailAddress);
                message.setFrom(new InternetAddress(fromEmailAddress));

                VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("idsolicitud", 16);

                StringWriter stringWriter = new StringWriter();

                velocityEngine.mergeTemplate("templates/email.vm", "UTF-8", velocityContext, stringWriter);

                message.setSubject(subject);
                message.setText(stringWriter.toString(), true);
            }
        };

        try {
            mailSender.send(preparator);

            System.out.println("Email sending complete.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
