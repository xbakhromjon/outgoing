package uz.bakhromjon.department.dto;


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
public class DepartmentUpdateDTO {
    private UUID ID;
    private String name;
    private String shortName;
}
