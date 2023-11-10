package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SESEmailer implements Emailer {
  @Autowired
  private MailSender mailSender;

  @Override
  public void send(EmailToSend emailToSend) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setFrom("tomspencerlondon@gmail.com");
    simpleMailMessage.setTo(emailToSend.recipient());
    simpleMailMessage.setSubject(emailToSend.subject());
    simpleMailMessage.setText(emailToSend.body());

    this.mailSender.send(simpleMailMessage);
  }
}
