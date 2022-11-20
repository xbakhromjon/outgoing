package uz.bakhromjon.fishka.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FishkaCreateDTO {
    private Long workPlaceID;
    private Long orgID;
    private Integer fishkaType;
    private UUID fileID;
    private Boolean isVisible = true;
}
