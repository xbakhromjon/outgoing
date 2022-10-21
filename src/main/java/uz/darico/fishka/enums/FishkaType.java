package uz.darico.fishka.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uz.darico.exception.exception.UniversalException;
import uz.darico.sender.SenderStatus;

import java.util.EnumSet;
import java.util.Optional;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 10/10/22, Mon, 09:38
 **/
@RequiredArgsConstructor
@Getter
public enum FishkaType {
    BOSS("BOSS", 1),
    CHIEF_1("CHIEF_1", 2),
    CHIEF_2("CHIEF_2", 3),
    CHIEF_3("CHIEF_3", 4);

    private final String name;
    private final Integer code;


    public static String toFishkaType(Integer code) {
        EnumSet<FishkaType> fishkaTypes = EnumSet.allOf(FishkaType.class);
        Optional<FishkaType> optional = fishkaTypes.stream().filter(item -> item.getCode().equals(code)).findFirst();
        return optional.orElseThrow(() -> {
            throw new UniversalException(String.format("%s Fishka Type Code incorrect", code), HttpStatus.BAD_REQUEST);
        }).getName();
    }

}


