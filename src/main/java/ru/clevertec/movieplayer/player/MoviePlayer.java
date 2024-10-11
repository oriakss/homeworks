package ru.clevertec.movieplayer.player;

import lombok.AllArgsConstructor;
import ru.clevertec.movieplayer.source.InputDataSource;

@AllArgsConstructor
public class MoviePlayer {

    private InputDataSource source;

    public void startMovie() {
        source.open();
    }
}