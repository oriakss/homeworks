package util;

import by.clevertec.common.helper.DateSupplier;

import java.time.OffsetDateTime;

public class DateSupplierTest implements DateSupplier {

    @Override
    public OffsetDateTime getCurrentDateTime() {
        return OffsetDateTime.parse("2020-06-10T15:15:15.151515Z");
    }
}