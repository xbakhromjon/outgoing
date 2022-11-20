package uz.bakhromjon.feign.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 21/10/22, Fri, 10:32
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    private String systemName;
    private Integer rank;
    private String name;
}
