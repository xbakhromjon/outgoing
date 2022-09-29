package uz.darico.missiveFile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.contentFile.ContentFile;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveFileCreateDTO {
    private UUID missiveID;
    private Long workPlaceID;
    private String content;
}
