package by.clevertec.service;

import by.clevertec.common.helper.DateSupplier;
import by.clevertec.domain.Cake;
import by.clevertec.entity.CakeEntity;
import by.clevertec.exception.CakeNotFoundException;
import lombok.RequiredArgsConstructor;
import by.clevertec.mapper.CakeMapper;
import org.springframework.stereotype.Service;
import by.clevertec.repository.CakeRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CakeService {

    private final CakeRepository cakeRepository;
    private final CakeMapper cakeMapper;
    private final DateSupplier dateSupplier;

    public List<Cake> findCakes() {
        List<CakeEntity> cakes = cakeRepository.findCakes().stream()
                .filter(cake -> cake.getExpiredPeriod().isAfter(dateSupplier.getCurrentDateTime()))
                .toList();
        return cakeMapper.toDomains(cakes);
    }

    public Cake findCakeById(UUID id) {
        CakeEntity cake = cakeRepository.findCakeById(id)
                .orElseThrow(() -> CakeNotFoundException.byCakeId(id));
        return cakeMapper.toDomain(cake);
    }

    public Cake createCake(Cake cake) {
        CakeEntity cakeEntity = cakeMapper.toEntity(cake);
        CakeEntity createdEntity = cakeRepository.createCake(cakeEntity);
        return cakeMapper.toDomain(createdEntity);
    }

    public Cake updateCake(UUID id, Cake newCake) {
        CakeEntity cakeEntity = cakeMapper.toEntity(newCake);
        CakeEntity updatedEntity = cakeRepository.updateCake(id, cakeEntity);
        return cakeMapper.toDomain(updatedEntity);
    }

    public void deleteCake(UUID cakeId) {
        cakeRepository.deleteCake(cakeId);
    }
}