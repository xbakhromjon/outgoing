package uz.bakhromjon.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.base.entity.Auditable;
import uz.bakhromjon.permission.Permission;
import uz.bakhromjon.role.Role;
import uz.bakhromjon.user.User;
import uz.bakhromjon.userPosition.UserPosition;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author : Bakhromjon Khasanboyev
 **/

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkPlace extends Auditable {
    @OneToOne
    private User user;
    private Boolean isAttached = false;
    @ManyToOne
    private Role role;
    @OneToOne
    private UserPosition userPosition;
    @ManyToMany
    private List<Permission> permissions;
}
