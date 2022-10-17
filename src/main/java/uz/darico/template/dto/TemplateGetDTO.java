package uz.darico.template.dto;

import lombok.*;
import uz.darico.contentFile.ContentFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TemplateGetDTO {
    private UUID ID;
    private Long workPlaceID;
    private String content;
    private String name;
    private ContentFile image;
    private Boolean isGlobal;
    private LocalDateTime createdAt;
}
