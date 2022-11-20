package uz.bakhromjon.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.bakhromjon.base.entity.AbstractEntity;

import javax.persistence.*;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:39
 **/
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    private Integer rank;

    public Role() {

    }

    public Role(ERole name, Integer rank) {
        this.name = name;
        this.rank = rank;
    }
}
