package uz.darico.missiveFile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.contentFile.ContentFile;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 03/10/22, Mon, 17:17
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveFileGetDTO {
    private ContentFile file;
    private Integer version;
    private String rejectedPurpose;
    private String rejectedFirstName;
    private String rejectedLastName;
    private String rejectedMiddleName;

    public MissiveFileGetDTO(ContentFile file, Integer version, String rejectedPurpose) {
        this.file = file;
        this.version = version;
        this.rejectedPurpose = rejectedPurpose;
    }
}
