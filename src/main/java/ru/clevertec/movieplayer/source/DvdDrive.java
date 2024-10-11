package ru.clevertec.movieplayer.source;

public class DvdDrive implements InputDataSource {

    @Override
    public void open() {
        System.out.println("Playing movie from DVD-drive");
    }
}