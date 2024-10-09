package by.clevertec.service;

import by.clevertec.common.helper.DateSupplier;
import by.clevertec.domain.Cake;
import by.clevertec.entity.CakeEntity;
import by.clevertec.exception.CakeNotFoundException;
import by.clevertec.mapper.CakeMapper;
import by.clevertec.repository.CakeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DateSupplierTest;
import util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
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

    @Test
    void shouldFindCakeById() {
        //given
        CakeEntity cakeEntity = TestData.getCakeEntity();
        Cake cake = TestData.getCake();

        when(cakeRepository.findCakeById(cake.getId()))
                .thenReturn(Optional.of(cakeEntity));
        when(cakeMapper.toDomain(cakeEntity))
                .thenReturn(cake);

        //when
        Cake actualCake = cakeService.findCakeById(cake.getId());

        //then
        assertEquals(cake, actualCake);

        verify(cakeRepository).findCakeById(cake.getId());
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
        CakeEntity cakeEntity = TestData.getCakeEntity();
        Cake cake = TestData.getCake();

        when(cakeRepository.createCake(cakeEntity)).thenReturn(cakeEntity);
        when(cakeMapper.toEntity(cake)).thenReturn(cakeEntity);
        when(cakeMapper.toDomain(cakeEntity)).thenReturn(cake);

        //when
        Cake actualCake = cakeService.createCake(cake);

        //then


        assertEquals(cake, actualCake);

        verify(cakeRepository).createCake(cakeEntity);
        verifyNoMoreInteractions(cakeRepository);
    }

//    @Test
//    void shouldUpdateCake() {
//        //given
//        CakeEntity cakeEntity = TestData.getCakeEntity();
//        Cake cake = TestData.getCake();
//
//        when(cakeRepository.updateCake(cakeEntity.getId(), cakeEntity))
//                .thenReturn(cakeEntity);
//        when(cakeMapper.toDomain(cakeEntity))
//                .thenReturn(cake);
//
//        //when
//        Cake actualCake = cakeService.updateCake(cake.getId(), cake);
//
//        //then
//        assertEquals(cake, actualCake);
//
//        verify(cakeRepository).createCake(cakeEntity);
//        verifyNoMoreInteractions(cakeRepository);
//    }

    @Test
    void shouldDeleteCakeById() {
        //given
        UUID cakeId = UUID.randomUUID();

        //when
        cakeService.deleteCake(cakeId);

        //then
        verify(cakeRepository).deleteCake(cakeId);
    }

//    @MethodSource("getArgumentsForTest")
//    @ParameterizedTest
//    void test(UUID cakeId, Cake cake, CakeEntity cakeEntity) {
//        //given
//
////        when(cakeRepository.findCakeById(cakeId))
////                .thenReturn(Optional.ofNullable(cakeEntity));
////        when(cakeMapper.toDomain(cakeEntity))
////                .thenReturn(cake);
//
//        //when
//        Cake actualCake = cakeService.findCakeById(cakeId);
//
//        //then
//        assertThat(actualCake).isEqualTo(cake).isNotNull();
//
//    }
//
//    static Stream<Arguments> getArgumentsForTest() {
//        return Stream.of(
//                Arguments.of(
//                        UUID.fromString("3d77ab90-b7bb-40f6-a64d-e1f484e928d8"),
//                        TestData.getCake().setId(UUID.fromString("3d77ab90-b7bb-40f6-a64d-e1f484e928d8")),
//                        TestData.getCakeEntity().setId(UUID.fromString("3d77ab90-b7bb-40f6-a64d-e1f484e928d8"))
//                ),
//                Arguments.of(
//                        UUID.fromString("0b8d6dd9-1310-415c-9dbd-c623321836a5"),
//                        TestData.getCake().setId(UUID.fromString("0b8d6dd9-1310-415c-9dbd-c623321836a5")),
//                        TestData.getCakeEntity().setId(UUID.fromString("0b8d6dd9-1310-415c-9dbd-c623321836a5"))
//                )
//        );
//    }
}