package uz.bakhromjon.workplace.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.permission.Permission;
import uz.bakhromjon.role.Role;
import uz.bakhromjon.user.User;
import uz.bakhromjon.user.dto.UserGetDTO;
import uz.bakhromjon.userPosition.UserPosition;
import uz.bakhromjon.userPosition.dto.UserPositionGetDTO;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceGetDTO {
    private UserGetDTO user;
    private Role role;
    private UserPositionGetDTO userPosition;
    private List<Permission> permissions;
}
