package uz.bakhromjon.outReceiver.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutReceiverCreateDTO {
    private Long correspondentID;
    private String correspondentEmail;
    private String correspondentExat;
}
