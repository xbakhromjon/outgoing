package uz.bakhromjon.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissiveRejectDTO {
    private String rootMissiveID;
    private String missiveID;
//    private Long workPlaceID;
    private Long rejectedWorkPlaceID;
    private String message;
    private Integer rejectType;
}
