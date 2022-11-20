package uz.bakhromjon.outReceiver;

import lombok.*;
import uz.bakhromjon.base.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "out_receiver")
public class OutReceiver extends AbstractEntity {
    private Long correspondentID;
    private String correspondentEmail;
    private String correspondentExat;
}
