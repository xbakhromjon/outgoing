package uz.darico.missive.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 28/10/22, Fri, 12:44
 **/
@RequiredArgsConstructor
@Getter
public enum RejectType {
    SIGNATORY("SIGNATORY", 1),
    CONFIRMATIVE("CONFIRMATIVE", 2);

    private final String name;
    private final Integer code;
}
