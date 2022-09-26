package uz.darico.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissiveRejectDTO {
    private String missive;
    // confID or signatoryID
    private String message;
    private String rejectedBy;
    private UUID rejectedByUUID;
}
