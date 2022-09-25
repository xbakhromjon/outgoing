package uz.darico.confirmative;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ConfirmativeStatus {
    CONFIRMED(1),
    REJECTED(2),
    NOT_VIEWED(3),
    VIEWED(4);

    private final Integer code;
}
