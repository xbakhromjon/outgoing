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
public class MissiveRawDTO {
    private UUID ID;
    private Long orgID;
    private Long workPlace;
    private Long signatoryWorkPlace;
    private List<Long> confirmativeWorkPlaces;
    private List<Long> outReceivers;
    private List<Long> inReceivers;
    private List<ContentFile> baseFiles;
    private String missiveContent;
}
