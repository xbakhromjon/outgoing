package uz.bakhromjon.fishka.dto;

import lombok.*;
import uz.bakhromjon.contentFile.ContentFile;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FishkaGetDTO {
    private UUID ID;
    private String fishkaType;
    private ContentFile file;
    private Boolean isVisible;
}
