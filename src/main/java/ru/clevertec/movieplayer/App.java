package ru.clevertec.movieplayer;

import ru.clevertec.movieplayer.player.MoviePlayer;
import ru.clevertec.movieplayer.source.DvdDrive;
import ru.clevertec.movieplayer.source.InputDataSource;
import ru.clevertec.movieplayer.source.UsbToInputDataSourceAdapter;

public class App {

    public static void main(String[] args) {
        InputDataSource dvdDriveSource = new DvdDrive();
        InputDataSource usbCardSource = new UsbToInputDataSourceAdapter();

        MoviePlayer moviePlayer1 = new MoviePlayer(dvdDriveSource);
        MoviePlayer moviePlayer2 = new MoviePlayer(usbCardSource);

        moviePlayer1.startMovie();
        moviePlayer2.startMovie();
    }
}