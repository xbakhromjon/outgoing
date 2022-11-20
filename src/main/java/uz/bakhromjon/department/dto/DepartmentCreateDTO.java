package uz.bakhromjon.department.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentCreateDTO {
    private String name;
    private String shortName;
}
