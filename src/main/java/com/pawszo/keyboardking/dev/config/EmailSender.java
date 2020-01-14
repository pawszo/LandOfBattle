package com.pawszo.keyboardking.dev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSender implements IEmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String content) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo("admin@landofbattle.pl");
            helper.setFrom("noreply@landofbattle.pl");
            helper.setSubject(subject);
            helper.setText(content, true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mail);
    }
}
