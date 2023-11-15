package com.tomspencerlondon.moviebooker.moviegoer.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {

  @Value("${spring.email.host}")
  private String emailHost;

  @Value("${spring.mail.port}")
  private int port;

  @Value("${spring.mail.username}")
  private String userName;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.properties.mail.transport.protocol}")
  private String protocol;

  @Value("${spring.mail.properties.mail.smtp.auth}")
  private String auth;

  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private String enable;

  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(emailHost);
    mailSender.setPort(port);

    mailSender.setUsername(userName);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", protocol);
    props.put("mail.smtp.auth", auth);
    props.put("mail.smtp.starttls.enable", enable);
    props.put("mail.debug", "true");

    return mailSender;
  }

}
