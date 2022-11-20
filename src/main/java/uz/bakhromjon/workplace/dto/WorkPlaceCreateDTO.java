package uz.bakhromjon.workplace.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.role.Role;
import uz.bakhromjon.user.User;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceCreateDTO {
    private UUID departmentID;
    private Integer count;
}
