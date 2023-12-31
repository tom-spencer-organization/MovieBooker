package com.tomspencerlondon.moviebooker.common.hexagon.application.port;

import com.tomspencerlondon.moviebooker.admin.hexagon.domain.AdminProgram;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.Screen;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository {
    List<Screen> findAll();

    Screen findById(Long id);

    Screen save(Screen screen);
}
