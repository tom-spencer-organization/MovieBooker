package com.tomspencerlondon.moviebooker.hexagon.domain;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class AmendBookingTransactionTest {

    @Test
    void amendBookingCreatesPaymentWithUpdatedLoyaltyPoints() {

        Booking booking = createBooking(2, 2);
        MovieGoer movieGoer = createMovieGoer(2);
        int extraSeats = 2;
        AmendBookingTransaction amendBooking = new AmendBookingTransaction(booking, movieGoer, extraSeats, LocalDateTime.now());

        Payment payment = amendBooking.payment();

        assertThat(payment.amountPaid())
                .isEqualTo(new BigDecimal(10));
        assertThat(payment.updatedLoyaltyPoints())
                .isEqualTo(4);
    }

    @Test
    void amendBookingUsesLoyaltyPointsForFreeSeat() {
        Booking booking = createBooking(2, 2);
        MovieGoer movieGoer = createMovieGoer(5);

        int extraSeats = 2;
        AmendBookingTransaction amendBooking = new AmendBookingTransaction(booking, movieGoer, extraSeats, LocalDateTime.now());

        Payment payment = amendBooking.payment();

        assertThat(payment.amountPaid())
                .isEqualTo(new BigDecimal(5));
        assertThat(payment.updatedLoyaltyPoints())
                .isEqualTo(1);
    }

    private static MovieGoer createMovieGoer(int loyaltyPoints) {
        return new MovieGoer(
                null, null, "Tom", "password",
                loyaltyPoints, true,
                true, Role.USER);
    }

    private Booking createBooking(int seatsAvailable, int numberOfSeats) {
        Movie movie = new Movie(1L, "Godfather", "image", LocalDate.MIN, "description");
        MovieProgram movieProgram = new MovieProgram(
                1L,
                LocalDateTime.now(),
                seatsAvailable,
                movie,
                0,
                new BigDecimal(5)
        );
        Booking booking = new Booking(1L,
                movieProgram,
                numberOfSeats,
                new BigDecimal(5)
        );
        return booking;
    }
}