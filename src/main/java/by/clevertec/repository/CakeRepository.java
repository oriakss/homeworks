package by.clevertec.repository;

import by.clevertec.common.CakeType;
import by.clevertec.entity.CakeEntity;
import by.clevertec.exception.CakeNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CakeRepository {

    private static final List<CakeEntity> db = new ArrayList<>(List.of(
            new CakeEntity(UUID.randomUUID(), "cake1", CakeType.BIG, OffsetDateTime.now()),
            new CakeEntity(UUID.randomUUID(), "cake2", CakeType.BIG, OffsetDateTime.now()),
            new CakeEntity(UUID.randomUUID(), "cake3", CakeType.SMALL, OffsetDateTime.now())
    ));

    public List<CakeEntity> findCakes() {
        return db;
    }

    public Optional<CakeEntity> findCakeById(UUID id) {
        return db.stream()
                .filter(cake -> cake.getId().equals(id))
                .findFirst();
    }

    public CakeEntity createCake(CakeEntity cakeEntity) {
        db.add(cakeEntity);
        return cakeEntity;
    }

    public CakeEntity updateCake(UUID id, CakeEntity newCakeEntity) {
        return db.stream()
                .filter(cake -> cake.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> CakeNotFoundException.byCakeId(id))
                .setId(id)
                .setTitle(newCakeEntity.getTitle())
                .setCakeType(newCakeEntity.getCakeType())
                .setExpiredPeriod(newCakeEntity.getExpiredPeriod());
    }

    public void deleteCake(UUID id) {
        CakeEntity cakeEntity = db.stream()
                .filter(cake -> cake.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> CakeNotFoundException.byCakeId(id));
        db.remove(cakeEntity);
    }
}