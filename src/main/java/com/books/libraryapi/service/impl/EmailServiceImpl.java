package com.books.libraryapi.service.impl;

import com.books.libraryapi.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${application.mail.default-sender}")
    private String sender;

    @Override
    public void sendEmails(String message, List<String> mailList) {

        String[] mailListArray = mailList.toArray(new String[mailList.size()]);
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setSubject("Late book return");
        mailMessage.setText(message);
        mailMessage.setTo(mailListArray);

        javaMailSender.send(mailMessage);
    }
}
