package uz.darico.signatory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uz.darico.exception.exception.UniversalException;
import uz.darico.sender.SenderStatus;

import java.util.EnumSet;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum SignatoryStatus {
    SIGNED("SIGNED", 1),
    REJECTED("REJECTED",2),
    NOT_VIEWED("NOT_VIEWED", 3),
    VIEWED( "VIEWED", 4);

    private final String name;
    private final Integer code;

    public static String toSignatoryStatus(Integer code) {
        EnumSet<SignatoryStatus> statuses = EnumSet.allOf(SignatoryStatus.class);
        Optional<SignatoryStatus> optional = statuses.stream().filter(item -> item.getCode().equals(code)).findFirst();
        return optional.orElseThrow(() -> {
            throw new UniversalException(String.format("%s Signatory Status Code incorrect", code), HttpStatus.BAD_REQUEST);
        }).getName();
    }
}
