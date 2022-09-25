package uz.darico.inReceiver;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "in_receiver")
public class InReceiver extends AbstractEntity {
    private Long correspondentID;
}
