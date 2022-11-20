package uz.bakhromjon.auth.dto;

/**
 * @author : Bakhromjon Khasanboyev
 **/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    private String username;
    private String password;
    private String email;
    private Set<String> role;
}

