package uz.bakhromjon.workplace.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class WorkPlaceUpdateDTO {
    private UUID ID;
    private UUID userID;
    private UUID roleID;
    private UUID userPositionID;
    private List<UUID> permissionsID;
}
