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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveGetDTO {
    private String orgName;
    private SenderGetDTO sender;
    private SignatoryGetDTO signatory;
    private List<ConfirmativeGetDTO> confirmatives;
    private String departmentName;
    private List<OutReceiverGetDTO> outReceivers;
    private List<InReceiverGetDTO> inReceivers;
    private List<ContentFile> baseFiles;
    private List<MissiveFileGetDTO> missiveFiles;
    private LocalDate createdAt;
}
