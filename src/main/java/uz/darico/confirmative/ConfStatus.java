package uz.darico.confirmative;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ConfStatus {
    CONFIRMED("CONFIRMED", 1),
    REJECTED("REJECTED", 2),
    NOT_VIEWED("NOT_VIEWED", 3),
    VIEWED( "VIEWED", 4);

    private final String name;
    private final Integer code;
}
