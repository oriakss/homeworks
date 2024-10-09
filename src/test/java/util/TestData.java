package util;

import by.clevertec.common.CakeType;
import by.clevertec.domain.Cake;
import by.clevertec.entity.CakeEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

public class TestData {

    private static final OffsetDateTime EXPIRED_PERIOD = new DateSupplierTest().getCurrentDateTime().plusDays(30);

    public static Cake getCake() {
        return new Cake()
                .setId(UUID.fromString("3d77ab90-b7bb-40f6-a64d-e1f484e928d8"))
                .setTitle("Cake1")
                .setCakeType(CakeType.BIG)
                .setExpiredPeriod(EXPIRED_PERIOD);
    }

    public static CakeEntity getCakeEntity() {
        return new CakeEntity()
                .setId(UUID.fromString("3d77ab90-b7bb-40f6-a64d-e1f484e928d8"))
                .setTitle("Cake1")
                .setCakeType(CakeType.BIG)
                .setExpiredPeriod(EXPIRED_PERIOD);
    }
}