package uz.darico.template.dto;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.contentFile.ContentFile;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TemplateCreateDTO {
    private Long workPlaceID;
    private UUID fileID;
    private String createdPurpose;
}
