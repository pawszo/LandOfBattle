package com.pawszo.keyboardking.dev.config;

public interface IEmailSender {

    void sendEmail(String to, String subject, String content);
}
