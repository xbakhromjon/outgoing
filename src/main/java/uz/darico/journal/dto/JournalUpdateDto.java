package uz.darico.journal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalUpdateDto {
    private UUID ID;
    private String uzName;
    private String ruName;
    private String shortDescription;
    private String journalPrefix;
    private String journalPostfix;
    private Integer beginNumber;
}
