package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.email;

import lombok.extern.slf4j.Slf4j;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SESEmailer implements Emailer {

  @Value("${fromEmail}")
  private String fromEmail;

  @Value("${fromName}")
  private String fromName;


  @Autowired
  private JavaMailSender emailSender;

  @Override
  @Async
  public void send(EmailToSend emailToSend) {
    try {
      MimeMessage message = emailSender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(message);

      helper.setFrom(fromEmail, fromName);
      helper.setTo(emailToSend.recipient());
      helper.setSubject(emailToSend.subject());
      helper.setText(emailToSend.body(), true);

      emailSender.send(message);

    }
    catch (Exception e) {
      log.error("Exception: " + e.getMessage());
    }
  }
}
