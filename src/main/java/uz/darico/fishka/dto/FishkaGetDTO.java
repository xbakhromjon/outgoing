package uz.darico.fishka.dto;

import lombok.*;
import uz.darico.contentFile.ContentFile;

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
