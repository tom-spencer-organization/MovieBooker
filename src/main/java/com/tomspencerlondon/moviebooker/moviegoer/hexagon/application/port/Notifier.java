package com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.port;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Booking;

public interface Notifier {

  void confirmBooking(Booking booking);
}
