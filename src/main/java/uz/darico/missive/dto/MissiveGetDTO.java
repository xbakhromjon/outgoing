package uz.darico.missive.dto;

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

public class MissiveGetDTO {
    private Long orgID;
    @OneToOne
    private Sender sender;
    @OneToOne
    private Signatory signatory;
    @OneToMany
    private List<Confirmative> confirmatives;
    private Long departmentID;
    @OneToMany
    private List<OutReceiver> outReceivers;
    @OneToMany
    private List<InReceiver> inReceivers;
    @OneToMany
    private List<ContentFile> baseFiles;
    @OneToMany
    private List<MissiveFile> missiveFiles;
    private Boolean isReady;
}
