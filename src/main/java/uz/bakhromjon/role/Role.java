package uz.bakhromjon.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 31/10/22, Mon, 21:39
 **/
@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
