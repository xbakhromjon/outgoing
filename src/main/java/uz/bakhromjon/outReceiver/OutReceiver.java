package uz.bakhromjon.outReceiver;

import lombok.*;
import uz.bakhromjon.base.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "out_receiver")
public class OutReceiver extends AbstractEntity {
    private UUID correspondentID;
    private String correspondentEmail;
    private String correspondentExat;
}
