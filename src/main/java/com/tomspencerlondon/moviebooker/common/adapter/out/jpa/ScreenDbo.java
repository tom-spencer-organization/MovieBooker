package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "screen")
public class ScreenDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id", nullable = false)
    private Long screenId;

    private int numberOfSeats;

    private String name;

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
