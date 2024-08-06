package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.client.order.OrderClient;
import ru.yandex.practicum.model.order.Order;
import ru.yandex.practicum.service.OrderGenerator;

import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.practicum.constant.ScooterColorUtils.COLOR_BLACK;
import static ru.yandex.practicum.constant.ScooterColorUtils.COLOR_GREY;

@Slf4j
@RunWith(Parameterized.class)
public class OrderCreateTest {
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    public static final String FIELD_TRACK = "track";
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private final OrderClient orderClient = new OrderClient();
    private final OrderGenerator generator = new OrderGenerator();
    private final UtilMethods util = new UtilMethods();
    private Integer trackId;

    public OrderCreateTest(String firstName, String lastName, String address, String metroStation, String phone,
                           Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "{index} : color {7}") //индекс поля "комментарий" в массиве данных для тестирования
    public static Object[][] getParameters() {
        return new Object[][]{
                {"Иван", "Иванов", "Адрес 1", "Красные ворота", "79999999991", 1, "2024-08-08", "black", new String[]{COLOR_BLACK}},
                {"Ольга", "Петрова", "Адрес 2", "Чистые пруды", "79999999992", 2, "2024-08-08", "grey", new String[]{COLOR_GREY}},
                {"Дмитрий", "Димов", "Адрес 3", "Охотный ряд", "79999999993", 3, "2024-08-08", "black and grey", new String[]{COLOR_BLACK, COLOR_GREY}},
                {"Анна", "Карпова", "Адрес 4", "Соколиная гора", "779999999994", 4, "2024-08-08", "не указан", new String[]{}},
                {"Георгий", "Мао", "Адрес 5", "Красные ворота", "79999999995", 5, "2024-08-08", "null", null}
        };
    }

    @After
    public void delete() {
        if (trackId != null && trackId > 0) {
            util.cancelOrder(trackId);
        }
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder() {
        Order order = generator.getOrder(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, color);
        log.info("Создание заказа: {}", order);

        Response response = orderClient.createOrder(order);
        log.info(RESPONSE, response.body().asString());

        trackId = response.body().path(FIELD_TRACK);
        log.info("Создан заказ №: {}\n", trackId);

        response.then().statusCode(HttpStatus.SC_CREATED)
                .and().assertThat().body(FIELD_TRACK, notNullValue());
    }
}