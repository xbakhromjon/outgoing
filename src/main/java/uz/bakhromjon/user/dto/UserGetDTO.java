package uz.bakhromjon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.contentFile.ContentFile;

import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDTO {
    private UUID id;
    private String username;
    private String email;
    private ContentFile avatar;
    private String firstname;
    private String lastname;
}
