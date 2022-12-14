package uz.bakhromjon.feign.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 15/10/22, Sat, 16:03
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceShortInfo {
    private Long orgID;
    private String departmentName;
    private String positionName;
    private String firstName;
    private String lastName;
    private String middleName;
    private List<UserRole> roles;
}
