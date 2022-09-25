package uz.darico.signatory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum SignatoryStatus {
    SIGNED(1),
    REJECTED(2);

    private final Integer code;
}
