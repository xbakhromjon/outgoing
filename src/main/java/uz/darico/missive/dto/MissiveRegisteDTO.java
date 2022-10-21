package uz.darico.missive.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveRegisteDTO {
    private UUID id;
    private UUID journalID;
    private String number;
    private LocalDate registeredAt;
}