package uz.bakhromjon.missiveFile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
