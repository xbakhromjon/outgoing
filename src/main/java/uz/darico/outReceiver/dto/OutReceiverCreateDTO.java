package uz.darico.outReceiver.dto;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

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
