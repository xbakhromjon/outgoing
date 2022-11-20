package uz.bakhromjon.inReceiver.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InReceiverCreateDTO {
    private UUID correspondentID;
}
