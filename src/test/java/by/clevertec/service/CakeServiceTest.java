package by.clevertec.service;

import by.clevertec.common.helper.DateSupplier;
import by.clevertec.domain.Cake;
import by.clevertec.entity.CakeEntity;
import by.clevertec.exception.CakeNotFoundException;
import by.clevertec.mapper.CakeMapper;
import by.clevertec.repository.CakeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DateSupplierTest;
import util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakeServiceTest {

    @Mock
    private CakeRepository cakeRepository;

    @Mock
    private CakeMapper cakeMapper;

    @Spy
    private DateSupplier dateSupplier = new DateSupplierTest();

    @InjectMocks
    private CakeService cakeService;

    @Test
    void shouldFindCakes() {
        //given
        List<Cake> cakes = List.of(TestData.getCake());
        List<CakeEntity> cakeEntities = List.of(TestData.getCakeEntity());

        when(cakeRepository.findCakes())
                .thenReturn(cakeEntities);
        when(cakeMapper.toDomains(cakeEntities))
                .thenReturn(cakes);

        //when
        List<Cake> actualCakes = cakeService.findCakes();

        //then
        assertFalse(actualCakes.isEmpty());
    }

    @MethodSource("util.TestData#getArgumentsForTest")
    @ParameterizedTest
    void shouldFindCakeById(UUID cakeId, Cake cake, CakeEntity cakeEntity) {
        //given
        when(cakeRepository.findCakeById(cakeId))
                .thenReturn(Optional.ofNullable(cakeEntity));
        when(cakeMapper.toDomain(cakeEntity))
                .thenReturn(cake);

        //when
        Cake actualCake = cakeService.findCakeById(cakeId);

        //then
        assertEquals(cake, actualCake);

        verify(cakeRepository).findCakeById(cakeId);
        verifyNoMoreInteractions(cakeRepository);
    }

    @Test
    void shouldThrowCakeNotFoundException_WhenCakeNotFound() {
        //given
        UUID cakeId = UUID.randomUUID();

        when(cakeRepository.findCakeById(cakeId))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(CakeNotFoundException.class, () -> cakeService.findCakeById(cakeId));

        verifyNoInteractions(cakeMapper);
    }

    @Test
    void shouldCreateCake() {
        //given
        Cake cake = TestData.getCake();
        CakeEntity cakeEntity = TestData.getCakeEntity();

        when(cakeRepository.createCake(cakeEntity))
                .thenReturn(cakeEntity);
        when(cakeMapper.toEntity(cake))
                .thenReturn(cakeEntity);
        when(cakeMapper.toDomain(cakeEntity))
                .thenReturn(cake);

        //when
        Cake actualCake = cakeService.createCake(cake);

        //then
        assertThat(actualCake).isEqualTo(cake).isNotNull();

        verify(cakeRepository).createCake(cakeEntity);
        verifyNoMoreInteractions(cakeRepository);
    }

    @MethodSource("util.TestData#getArgumentsForTest")
    @ParameterizedTest
    void shouldUpdateCake(UUID cakeId, Cake cake, CakeEntity cakeEntity) {
        //given
        when(cakeRepository.updateCake(cakeId, cakeEntity))
                .thenReturn(cakeEntity);
        when(cakeMapper.toEntity(cake))
                .thenReturn(cakeEntity);
        when(cakeMapper.toDomain(cakeEntity))
                .thenReturn(cake);

        //when
        Cake actualCake = cakeService.updateCake(cakeId, cake);

        //then
        assertThat(actualCake).isEqualTo(cake).isNotNull();

        verify(cakeRepository).updateCake(cakeId, cakeEntity);
        verifyNoMoreInteractions(cakeRepository);
    }

    @Test
    void shouldDeleteCakeById() {
        //given
        UUID cakeId = UUID.randomUUID();

        //when
        cakeService.deleteCake(cakeId);

        //then
        verify(cakeRepository).deleteCake(cakeId);
    }
}