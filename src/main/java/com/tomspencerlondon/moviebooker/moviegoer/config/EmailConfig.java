package com.tomspencerlondon.moviebooker.moviegoer.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {


  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("email-smtp.us-west-2.amazonaws.com");
    mailSender.setPort(587);

    mailSender.setUsername("AKIA2IZA3UHTSQDP664V");
    mailSender.setPassword("BJzm8opaJH69urs2Lx8pgKx+bHjx/l5zETonL4xZRHVl");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }

}
