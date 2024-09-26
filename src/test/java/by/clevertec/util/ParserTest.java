package by.clevertec.util;

import by.clevertec.model.Product;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    void shouldParseObjectToJson() {
        //given
        Product product = new Product(UUID.randomUUID(), "Product", 456.99, Map.of(UUID.randomUUID(), BigDecimal.ZERO));

        //when
        String jsonFromCustomParser = Parser.parseObjectToJson(product);
        String jsonFromGson = new Gson().toJson(product);

        //then
        assertEquals(jsonFromGson, jsonFromCustomParser);
    }

    @Test
    void shouldParseJsonToObject() {
        //given
        String json = "{\"id\":\"1d802e8a-358c-4474-a77b-e0ad33abe260\",\"name\":\"Product\",\"price\":456.99,\"map\":{\"8454b73e-e8c4-4c25-b795-bd0f72eb5e2d\":0}}";

        //when
        Product productFromCustomParser = Parser.parseJsonToObject(json, Product.class);
        Product productFromGson = new Gson().fromJson(json, Product.class);

        //then
        assertEquals(productFromGson.toString(), productFromCustomParser.toString());
    }
}