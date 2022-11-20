package uz.bakhromjon.outReceiver.dto;

import lombok.*;
import org.checkerframework.checker.units.qual.UnknownUnits;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutReceiverCreateDTO {
    private UUID correspondentID;
    private String correspondentEmail;
    private String correspondentExat;
}
