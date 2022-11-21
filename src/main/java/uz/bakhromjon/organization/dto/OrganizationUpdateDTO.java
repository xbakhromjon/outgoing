package uz.bakhromjon.organization.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.contentFile.ContentFile;

import javax.persistence.OneToOne;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationUpdateDTO {
    private UUID id;
    private String name;
    private String shortName;
    private String address;
    private String mobileNumber;
    private String email;
    private UUID logoId;
    private String website;
}
