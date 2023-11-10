package com.tomspencerlondon.moviebooker.moviegoer.config;

import com.tomspencerlondon.moviebooker.common.hexagon.application.port.*;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.BookingService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.CustomUserDetailsService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.MovieGoerService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.MovieService;
import com.tomspencerlondon.moviebooker.moviegoer.hexagon.application.port.Notifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieConfig {

    @Bean
    public MovieService movieService(MovieRepository movieRepository, MovieProgramRepository movieProgramRepository) {
        return new MovieService(movieRepository, movieProgramRepository);
    }

    @Bean
    public BookingService bookingService(BookingRepository bookingRepository, MovieProgramRepository movieProgramRepository,
                                         MovieGoerRepository movieGoerRepository, PaymentRepository paymentRepository, Notifier notifier) {
        return new BookingService(bookingRepository, movieProgramRepository, movieGoerRepository, paymentRepository, notifier);
    }

    @Bean
    public MovieGoerService movieGoerService(MovieGoerRepository movieGoerRepository) {
        return new MovieGoerService(movieGoerRepository);
    }

    @Bean
    CustomUserDetailsService customUserDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }
}
