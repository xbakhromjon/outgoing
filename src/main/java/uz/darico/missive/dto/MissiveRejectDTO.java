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

    private String rejectedBy;
    // confID or signatoryID
    private String message;
    private UUID rejectedByUUID;
}
