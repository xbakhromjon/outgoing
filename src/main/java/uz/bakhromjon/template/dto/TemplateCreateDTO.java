package uz.bakhromjon.template.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TemplateCreateDTO {
    private UUID workPlaceID;
    private String content;
    private String name;
    private UUID fileID;
    private Boolean isGlobal;
}
