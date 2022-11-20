package uz.bakhromjon.journal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


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
    private UUID workPlaceID;
    private UUID orgId;
    private UUID userID;
}
