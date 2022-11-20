package uz.bakhromjon.inReceiver;

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
@Table(name = "in_receiver")
public class InReceiver extends AbstractEntity {
    private UUID correspondentID;
}
