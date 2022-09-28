package uz.darico.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.confirmative.Confirmative;
import uz.darico.contentFile.ContentFile;
import uz.darico.inReceiver.InReceiver;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.sender.Sender;
import uz.darico.signatory.Signatory;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveGetDTO {
    private Long orgID;
    private Sender sender;
    private Signatory signatory;
    private List<Confirmative> confirmatives;
    private Long departmentID;
    private List<OutReceiver> outReceivers;
    private List<InReceiver> inReceivers;
    private List<ContentFile> baseFiles;
    private List<MissiveFile> missiveFiles;
    private Boolean isReady;

}
