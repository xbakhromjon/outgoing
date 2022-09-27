package uz.darico.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.darico.contentFile.ContentFile;
import uz.darico.missiveFile.MissiveFile;

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
    private List<MissiveFile> missiveFiles;

}
