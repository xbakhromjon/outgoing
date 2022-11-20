package uz.bakhromjon.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.base.entity.Auditable;
import uz.bakhromjon.userPosition.UserPosition;
import uz.bakhromjon.workplace.WorkPlace;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department extends Auditable {
    private String name;
    private String shortName;
    private Integer employeeCount = 0;
    @OneToMany
    private List<WorkPlace> workPlaces;
    @OneToMany
    private List<UserPosition> userPositions;
}

