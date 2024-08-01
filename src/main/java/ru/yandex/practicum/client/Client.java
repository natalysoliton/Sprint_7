package ru.yandex.practicum.client;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class Client {
    public static final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    public static final String BASE_PATH = "/api/v1";

    public RequestSpecification spec() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(BASE_PATH);
    }
}