package by.clevertec;

import by.clevertec.model.Customer;
import by.clevertec.model.Order;
import by.clevertec.model.Product;
import by.clevertec.util.Parser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        String jsonFromProduct = Parser.parseObjectToJson(getProduct());
        Product product = Parser.parseJsonToObject(jsonFromProduct, Product.class);

        String jsonFromOrder = Parser.parseObjectToJson(getOrder());
        Order order = Parser.parseJsonToObject(jsonFromOrder, Order.class);


        String jsonFromCustomer = Parser.parseObjectToJson(getCustomer());
        Customer customer = Parser.parseJsonToObject(jsonFromCustomer, Customer.class);

        System.out.println("JSON from Product: " + jsonFromProduct);
        System.out.println("Product from JSON: " + product + '\n');

        System.out.println("JSON from Order: " + jsonFromOrder);
        System.out.println("Order from JSON: " + order + '\n');

        System.out.println("JSON from Customer: " + jsonFromCustomer);
        System.out.println("Customer from JSON: " + customer + '\n');
    }

    private static Customer getCustomer() {
        return new Customer(
                UUID.randomUUID(),
                "Sun",
                "Butterfly",
                LocalDate.of(1999, 9, 9),
                List.of(getOrder(), getOrder())
        );
    }

    private static Order getOrder() {
        return new Order(
                UUID.randomUUID(),
                List.of(
                        new Product(
                                UUID.randomUUID(),
                                "some product",
                                77.77,
                                Map.of(
                                        UUID.randomUUID(), BigDecimal.valueOf(333),
                                        UUID.randomUUID(), BigDecimal.valueOf(999))),
                        new Product(
                                UUID.randomUUID(),
                                "semki",
                                325.0,
                                Map.of(
                                        UUID.randomUUID(), BigDecimal.valueOf(880033)
                                )
                        ),
                        getProduct()
                ),
                OffsetDateTime.of(2024, 6, 25, 13, 55, 10, 333333, ZoneOffset.UTC)
        );
    }

    private static Product getProduct() {
        return new Product(
                UUID.randomUUID(),
                "Name",
                6.66,
                Map.of(
                        UUID.randomUUID(), BigDecimal.ZERO,
                        UUID.randomUUID(), BigDecimal.ONE,
                        UUID.randomUUID(), BigDecimal.TWO
                )
        );
    }
}