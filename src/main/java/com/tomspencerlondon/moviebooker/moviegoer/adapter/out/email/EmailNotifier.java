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

  }
}
