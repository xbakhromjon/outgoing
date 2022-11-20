package uz.bakhromjon.journal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.base.entity.Auditable;

import javax.persistence.Entity;

/**
 * @author Bakhromjon Fri, 6:11 PM 3/4/2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Journal extends Auditable {
    private String uzName;
    private String ruName;
    private String shortDescription;
    private String journalPrefix;
    private String journalPostfix;
    private Integer beginNumber;
    private Integer currentNumber;
    private Integer orderNumber;
    private Boolean closed = false;
    private Boolean archived = false;
    private String createdIPAddress;
    private String createdDevice;
    private String createdBrowser;
    private Long orgId;
    private Long userID;
}
