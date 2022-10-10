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
public class JournalDto  {
    private String uzName;
    private UUID id;
    private String ruName;
    private String shortDescription;
    private String journalPrefix;
    private String journalPostfix;
    private Integer beginNumber;
    private Integer orderNumber;
    private Boolean closed;
    private Boolean deleted;
    private Boolean archived;
    private String createdIpAddress;
    private String createdDevice;
    private String createdBrowser;
    private Long orgId;
    private Long createdId;
}
