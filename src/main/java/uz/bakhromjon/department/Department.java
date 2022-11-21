package uz.bakhromjon.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.base.entity.Auditable;
import uz.bakhromjon.user.User;
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
    @OneToMany
    private List<User> users;

    public Department(String name, String shortName, Integer employeeCount) {
        this.name = name;
        this.shortName = shortName;
        this.employeeCount = employeeCount;
    }
}

