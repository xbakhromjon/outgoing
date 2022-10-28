package uz.darico.missive.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissiveRejectDTO {
    private String rootMissiveID;
    private String missiveID;
    private Long workPlaceID;
    private Long rejectedWorkPlaceID;
    private String message;
    private String content;
    private Integer rejectType;
}
