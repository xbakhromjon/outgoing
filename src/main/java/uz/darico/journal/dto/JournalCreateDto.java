package uz.darico.journal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalCreateDto {
    private String uzName;
    private String ruName;
    private String shortDescription;
    private String journalPrefix;
    private String journalPostfix;
    private Integer beginNumber;
    private Integer orderNumber;
    private Long workPlaceID;
    private Long orgId;
    private Long userID;
}
