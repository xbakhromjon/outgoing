package uz.darico.sender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SenderStatus {
    SENT(1),
    NOT_SENT(2),
    PREPARING(3);

    private final Integer code;
}
