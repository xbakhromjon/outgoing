package uz.bakhromjon.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tab {
    HOMAKI(1),
    JARAYONDA(2),
    TASDIQLASH_UCHUN(3),
    TASDIQLANGAN(4),
    IMZOLASH_UCHUN(5),
    IMZOLANGAN(6),
    YUBORILGAN(7);

    private final Integer code;
}
