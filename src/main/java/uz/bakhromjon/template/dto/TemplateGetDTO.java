package uz.bakhromjon.template.dto;

import lombok.*;
import uz.bakhromjon.contentFile.ContentFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TemplateGetDTO {
    private UUID ID;
    private UUID workPlaceID;
    private String content;
    private String name;
    private ContentFile image;
    private Boolean isGlobal;
    private LocalDateTime createdAt;
}
