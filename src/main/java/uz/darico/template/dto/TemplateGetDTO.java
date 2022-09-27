package uz.darico.template.dto;

import lombok.*;
import uz.darico.contentFile.ContentFile;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TemplateGetDTO {
    private Long workPlaceID;
    private ContentFile file;
    private String createdPurpose;
}
