package uz.darico.missive;

import lombok.*;
import org.hibernate.annotations.Type;
import uz.darico.base.entity.Auditable;
import uz.darico.confirmative.Confirmative;
import uz.darico.contentFile.ContentFile;
import uz.darico.inReceiver.InReceiver;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.sender.Sender;
import uz.darico.signatory.Signatory;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "missive")
public class Missive extends Auditable {
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
    @OneToOne
    private MissiveFile missiveFile;
    private Boolean isReady = false;
    @Type(type = "text")
    private String shortInfo;
    private Integer version = 1;
    private Boolean isLastVersion = true;
    private UUID rootVersionID;

    public Missive(Long orgID, Sender sender, Signatory signatory, List<Confirmative> confirmatives,
                   Long departmentID, List<OutReceiver> outReceivers, List<InReceiver> inReceivers,
                   List<ContentFile> baseFiles, MissiveFile missiveFile) {
        this.orgID = orgID;
        this.sender = sender;
        this.signatory = signatory;
        this.confirmatives = confirmatives;
        this.departmentID = departmentID;
        this.outReceivers = outReceivers;
        this.inReceivers = inReceivers;
        this.baseFiles = baseFiles;
        this.missiveFile = missiveFile;
    }
}
