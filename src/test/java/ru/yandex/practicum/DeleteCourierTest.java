package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.practicum.client.courier.CourierClient;
import ru.yandex.practicum.model.courier.Courier;
import ru.yandex.practicum.service.CourierGenerator;

import static org.hamcrest.CoreMatchers.equalTo;

@Slf4j
public class DeleteCourierTest {
    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient courierClient = new CourierClient();
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_OK = "ok";
    private static final String MESSAGE_NOT_FOUND = "Курьера с таким id не существует.";
    private static final String RESPONSE = "Получен ответ от сервера: {}";
    private static final String DELETE = "Удаление курьера c id = {}";
    private Integer id = 0;

    @Test
    @DisplayName("Удаление курьера")
    public void deleteCourier() {
        Courier courier = generator.getCourier();
        courierClient.create(courier);
        id = courierClient.login(generator.getCourierForAuth(courier)).body().path("id");
        log.info(DELETE, id);

        Response response = courierClient.deleteCourier(id);
        log.info(RESPONSE, response.body().asString());

        response.then().assertThat().body(FIELD_OK, equalTo(true))
                .and().statusCode(HttpStatus.SC_OK);
        log.info(DELETE + " произошло корректно\n", id);
    }

    @Test
    @DisplayName("Удаление курьера по не существующему id")
    public void deleteNonExistent() {
        id = Integer.MIN_VALUE;
        log.info(DELETE, id);
        Response response = courierClient.deleteCourier(id);
        log.info(RESPONSE + "\n", response.body().asString());

        response.then().statusCode(HttpStatus.SC_NOT_FOUND)
                .and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_NOT_FOUND));
    }
}
