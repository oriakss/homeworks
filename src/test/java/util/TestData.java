package util;

import by.clevertec.common.CakeType;
import by.clevertec.domain.Cake;
import by.clevertec.entity.CakeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TestData {

    private static final OffsetDateTime EXPIRED_PERIOD = new DateSupplierTest().getCurrentDateTime().plusDays(30);

    public static Cake getCake() {
        return new Cake()
                .setId(UUID.randomUUID())
                .setTitle("Cake")
                .setCakeType(CakeType.BIG)
                .setExpiredPeriod(EXPIRED_PERIOD);
    }

    public static CakeEntity getCakeEntity() {
        return new CakeEntity()
                .setId(UUID.randomUUID())
                .setTitle("Some Cake")
                .setCakeType(CakeType.SMALL)
                .setExpiredPeriod(EXPIRED_PERIOD);
    }

    public static Stream<Arguments> getArgumentsForShouldFindCakeByIdAndShouldUpdateCakeTests() {
        return Stream.of(
                Arguments.of(
                        UUID.fromString("3d77ab90-b7bb-40f6-a64d-e1f484e928d8"),
                        getCake().setId(UUID.fromString("3d77ab90-b7bb-40f6-a64d-e1f484e928d8")),
                        getCakeEntity().setId(UUID.fromString("3d77ab90-b7bb-40f6-a64d-e1f484e928d8"))
                ),
                Arguments.of(
                        UUID.fromString("0b8d6dd9-1310-415c-9dbd-c623321836a5"),
                        getCake().setId(UUID.fromString("0b8d6dd9-1310-415c-9dbd-c623321836a5")),
                        getCakeEntity().setId(UUID.fromString("0b8d6dd9-1310-415c-9dbd-c623321836a5"))
                )
        );
    }

    public static Stream<Arguments> getArgumentsForShouldConvertEntitiesToDomainsTest() {
        return Stream.of(
                Arguments.of(
                        List.of(new Cake(), new Cake()),
                        List.of(new CakeEntity(), new CakeEntity())
                ),
                Arguments.of(
                        null,
                        null
                )
        );
    }

    public static Stream<Arguments> getArgumentsForShouldConvertEntityToDomainAndShouldConvertDomainToEntityTests() {
        return Stream.of(
                Arguments.of(
                        new Cake(),
                        new CakeEntity()
                ),
                Arguments.of(
                        null,
                        null
                )
        );
    }

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules();
    }
}