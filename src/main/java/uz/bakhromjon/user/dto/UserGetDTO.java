package uz.bakhromjon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.contentFile.dto.ContentFileGetDTO;

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
    private ContentFileGetDTO avatar;
    private String firstname;
    private String lastname;
}
