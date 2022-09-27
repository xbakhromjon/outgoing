package uz.darico.template.dto;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TemplateUpdateDTO {
    private UUID ID;
    private UUID fileID;
    private String createdPurpose;
}
