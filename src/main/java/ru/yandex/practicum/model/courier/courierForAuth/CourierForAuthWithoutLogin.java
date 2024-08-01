package ru.yandex.practicum.model.courier.courierForAuth;

import lombok.*;
import ru.yandex.practicum.model.courier.Courier;
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class CourierForAuthWithoutLogin {
    private String password;

    public CourierForAuthWithoutLogin(Courier courier) {

        this.password = courier.getPassword();
    }
}
