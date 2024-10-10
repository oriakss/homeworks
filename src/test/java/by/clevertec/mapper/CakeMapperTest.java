package by.clevertec.mapper;

import by.clevertec.domain.Cake;
import by.clevertec.entity.CakeEntity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CakeMapperTest {

    private final CakeMapper cakeMapper = new CakeMapperImpl();

    @MethodSource("util.TestData#getArgumentsForShouldConvertEntitiesToDomainsTest")
    @ParameterizedTest
    void shouldConvertEntitiesToDomains(List<Cake> cakes, List<CakeEntity> cakeEntities) {
        //given, when
        List<Cake> actualCakes = cakeMapper.toDomains(cakeEntities);

        //then
        assertEquals(cakes, actualCakes);
    }

    @MethodSource("util.TestData#getArgumentsForShouldConvertEntityToDomainAndShouldConvertDomainToEntityTests")
    @ParameterizedTest
    void shouldConvertEntityToDomain(Cake cake, CakeEntity cakeEntity) {
        //given, when
        Cake actualCake = cakeMapper.toDomain(cakeEntity);

        //then
        assertEquals(cake, actualCake);
    }

    @MethodSource("util.TestData#getArgumentsForShouldConvertEntityToDomainAndShouldConvertDomainToEntityTests")
    @ParameterizedTest
    void shouldConvertDomainToEntity(Cake cake, CakeEntity cakeEntity) {
        //given, when
        CakeEntity actualCakeEntity = cakeMapper.toEntity(cake);

        //then
        assertEquals(cakeEntity, actualCakeEntity);
    }
}