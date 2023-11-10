package com.tomspencerlondon.moviebooker.moviegoer.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import io.awspring.cloud.ses.SimpleEmailServiceMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
public class MailConfig {
  @Bean
  public AmazonSimpleEmailService amazonSimpleEmailService() {

    return AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(new ProfileCredentialsProvider())
        .withRegion(Regions.US_WEST_2)
        .build();
  }

  @Bean
  public MailSender mailSender(
      AmazonSimpleEmailService amazonSimpleEmailService) {
    return new SimpleEmailServiceMailSender(amazonSimpleEmailService);
  }
}
