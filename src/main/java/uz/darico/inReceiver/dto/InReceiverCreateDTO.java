package uz.darico.inReceiver.dto;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InReceiverCreateDTO {
    private Long correspondentID;
}
