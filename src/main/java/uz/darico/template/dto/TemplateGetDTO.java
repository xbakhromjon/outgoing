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
    private String content;
    private String name;
    private ContentFile image;
    private Boolean isGlobal;
}
