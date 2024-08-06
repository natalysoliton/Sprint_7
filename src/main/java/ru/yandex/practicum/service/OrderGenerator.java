package ru.yandex.practicum.service;

import ru.yandex.practicum.model.order.Order;
public class OrderGenerator {
    public Order getOrder(String firstName, String lastName, String address, String metroStation, String phone,
                          Integer rentTime, String deliveryDate, String comment, String[] color) {
        return Order.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .metroStation(metroStation)
                .phone(phone)
                .rentTime(rentTime)
                .deliveryDate(deliveryDate)
                .comment(comment)
                .color(color)
                .build();
    }
}