package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.email;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.port.Notifier;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class EmailNotifier implements Notifier {

  private final Emailer emailer;

  @Autowired
  private SpringTemplateEngine thymeleafTemplateEngine;

  @Autowired
  public EmailNotifier(Emailer emailer) {
    this.emailer = emailer;
  }

  @Override
  public void confirmBooking(Booking booking) {
    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("recipientName", "tomspencerlondon@gmail.com");
    String body = """
                Hi,
                                           
                Thank you for your payment for '%s' tickets to see %s on DATE at Clapham Picture House.
                We can confirm that we have made the booking. Look forward to seeing you then.
                """.formatted(
        booking.numberOfSeatsBooked(),
        booking.filmName());
    templateModel.put("text", body);
    templateModel.put("senderName", "tomspencerlondon@gmail.com");

    Context thymeleafContext = new Context();
    thymeleafContext.setVariables(templateModel);

    String htmlBody = thymeleafTemplateEngine.process("emails/paymentConfirmation", thymeleafContext);

    EmailToSend emailToSend = new EmailToSend("MovieBooker ticket booked!", htmlBody,
        "tomspencerlondon@gmail.com");
    emailer.send(emailToSend);
  }
}
