package ru.clevertec.movieplayer.source;

public class UsbToInputDataSourceAdapter extends UsbCard implements InputDataSource {

    @Override
    public void open() {
        openFile();
    }
}