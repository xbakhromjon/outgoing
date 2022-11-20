package uz.bakhromjon.userPosition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.base.entity.Auditable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author : Bakhromjon Khasanboyev
 **/

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserPosition extends Auditable {
    private String name;
}
