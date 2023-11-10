package com.tomspencerlondon.moviebooker.moviegoer.adapter.out.email;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.port.Notifier;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailNotifier implements Notifier {

  private final Emailer emailer;

  @Autowired
  public EmailNotifier(Emailer emailer) {
    this.emailer = emailer;
  }

  @Override
  public void confirmBooking(Booking booking) {
    String body = """
                Hi,
                                           
                Thank you for your payment for '%s' tickets to see %s on DATE at Clapham Picture House.
                We can confirm that we have made the booking. Look forward to seeing you then.
                """.formatted(
                    booking.numberOfSeatsBooked(),
                    booking.filmName());

    EmailToSend emailToSend = new EmailToSend("MovieBooker ticket booked!", body,
        "tomspencerlondon@gmail.com");
    emailer.send(emailToSend);
  }
}
