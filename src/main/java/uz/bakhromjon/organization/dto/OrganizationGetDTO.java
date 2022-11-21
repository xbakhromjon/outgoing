package uz.bakhromjon.organization.dto;


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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationGetDTO {
    private UUID id;
    private String name;
    private String shortName;
    private String address;
    private String mobileNumber;
    private String email;
    private ContentFileGetDTO logo;
    private String website;
}
