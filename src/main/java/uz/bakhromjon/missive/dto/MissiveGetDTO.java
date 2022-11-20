package uz.bakhromjon.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.confirmative.dto.ConfirmativeGetDTO;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.feedback.feedback.dto.FeedbackGetDTO;
import uz.bakhromjon.inReceiver.dto.InReceiverGetDTO;
import uz.bakhromjon.missiveFile.dto.MissiveFileGetDTO;
import uz.bakhromjon.outReceiver.dto.OutReceiverGetDTO;
import uz.bakhromjon.sender.dto.SenderGetDTO;
import uz.bakhromjon.signatory.dto.SignatoryGetDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveGetDTO {
    private UUID ID;
    private UUID rootMissiveID;
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
    private ContentFile readyPDF;
    private FeedbackGetDTO feedback;

    public MissiveGetDTO(UUID ID, UUID rootMissiveID, String orgName, String email, SenderGetDTO sender, SignatoryGetDTO signatory, List<ConfirmativeGetDTO> confirmatives, String departmentName, List<OutReceiverGetDTO> outReceivers,
                         List<InReceiverGetDTO> inReceivers, List<ContentFile> baseFiles, MissiveFileGetDTO missiveFiles, LocalDate createdAt,
                         ContentFile readyPDF) {
        this.ID = ID;
        this.rootMissiveID = rootMissiveID;
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
        this.readyPDF = readyPDF;
    }
}
