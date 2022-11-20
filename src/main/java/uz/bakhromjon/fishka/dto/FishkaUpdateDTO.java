package uz.bakhromjon.fishka.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FishkaUpdateDTO {
    private Long workPlaceID;
    private UUID id;
    private UUID fileID;
    private Boolean isVisible = true;
}
