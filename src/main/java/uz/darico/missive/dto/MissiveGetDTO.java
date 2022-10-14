package uz.darico.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.confirmative.dto.ConfirmativeGetDTO;
import uz.darico.contentFile.ContentFile;
import uz.darico.inReceiver.dto.InReceiverGetDTO;
import uz.darico.missiveFile.dto.MissiveFileGetDTO;
import uz.darico.outReceiver.dto.OutReceiverGetDTO;
import uz.darico.sender.dto.SenderGetDTO;
import uz.darico.signatory.dto.SignatoryGetDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveGetDTO {
    private UUID ID;
    private String orgName;
    private String email;
    private SenderGetDTO sender;
    private SignatoryGetDTO signatory;
    private List<ConfirmativeGetDTO> confirmatives;
    private String departmentName;
    private List<OutReceiverGetDTO> outReceivers;
    private List<InReceiverGetDTO> inReceivers;
    private List<ContentFile> baseFiles;
    private MissiveFileGetDTO missiveFile;
    private LocalDate createdAt;
    private List<MissiveVersionShortInfoDTO> versions;

    public MissiveGetDTO(UUID ID, String orgName, String email, SenderGetDTO sender, SignatoryGetDTO signatory, List<ConfirmativeGetDTO> confirmatives, String departmentName, List<OutReceiverGetDTO> outReceivers, List<InReceiverGetDTO> inReceivers, List<ContentFile> baseFiles, MissiveFileGetDTO missiveFiles, LocalDate createdAt) {
        this.ID = ID;
        this.orgName = orgName;
        this.email = email;
        this.sender = sender;
        this.signatory = signatory;
        this.confirmatives = confirmatives;
        this.departmentName = departmentName;
        this.outReceivers = outReceivers;
        this.inReceivers = inReceivers;
        this.baseFiles = baseFiles;
        this.missiveFile = missiveFiles;
        this.createdAt = createdAt;
    }
}
