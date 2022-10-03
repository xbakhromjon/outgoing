package uz.darico.sender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import uz.darico.exception.exception.UniversalException;

import java.util.EnumSet;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum SenderStatus {
    SENT("SENT", 1),
    NOT_SENT("NOT_SENT", 2),
    PREPARING("PREPARING", 3);

    private final String name;
    private final Integer code;

    public static String toSenderStatus(Integer code) {
        EnumSet<SenderStatus> senderStatuses = EnumSet.allOf(SenderStatus.class);
        Optional<SenderStatus> optional = senderStatuses.stream().filter(item -> item.getCode().equals(code)).findFirst();
        return optional.orElseThrow(() -> {
            throw new UniversalException("%s Sender Status Code incorrect".formatted(code), HttpStatus.BAD_REQUEST);
        }).getName();
    }
}
