package uz.bakhromjon.userPosition.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPositionGetDTO {
    private UUID id;
    private String name;
}
