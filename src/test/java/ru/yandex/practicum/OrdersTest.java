package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.practicum.client.order.OrderClient;

import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class OrdersTest {
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    public static final String FIELD_ORDERS = "orders";
    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrders() {
        Response response = orderClient.getOrders();
        log.info(RESPONSE, response.body().asString());
        response.then().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body(FIELD_ORDERS, notNullValue());
    }
}