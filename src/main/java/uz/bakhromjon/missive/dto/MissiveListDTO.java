package uz.bakhromjon.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.bakhromjon.contentFile.ContentFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissiveListDTO {
    private UUID ID;
    private Long orgID;
    private String departmentName;
    private String senderFirstName;
    private String senderLastName;
    private List<ConfirmativeShortInfoDTO> confirmatives;
    private String shortInfo;
    private List<String> correspondent;
    private List<ContentFile> baseFiles;
    //    private MissiveFile missiveFile;
    private Integer totalCount;
    private String number;
    private LocalDate registeredAt;
}
