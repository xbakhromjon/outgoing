package uz.bakhromjon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    private UUID createdBy;
    private String username;
    private String email;
    private String password;
    private UUID avatarId;
    private String firstname;
    private String lastname;
}
