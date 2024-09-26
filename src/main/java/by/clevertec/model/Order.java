package by.clevertec.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private UUID id;
    private List<Product> products;
    private OffsetDateTime createDate;
}