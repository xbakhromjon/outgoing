package uz.bakhromjon.missive;

import lombok.*;
import org.hibernate.annotations.Type;
import uz.bakhromjon.base.entity.Auditable;
import uz.bakhromjon.confirmative.Confirmative;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.inReceiver.InReceiver;
import uz.bakhromjon.missiveFile.MissiveFile;
import uz.bakhromjon.outReceiver.OutReceiver;
import uz.bakhromjon.sender.Sender;
import uz.bakhromjon.signatory.Signatory;

import javax.persistence.*;
import java.time.LocalDate;
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
    @ManyToMany
    private List<ContentFile> baseFiles;
    @OneToOne
    private MissiveFile missiveFile;
    private Boolean isReady = false;
    private Boolean isConfirmOfficeManager = false;
    @Type(type = "text")
    private String shortInfo;
    private Integer version = 1;
    private Boolean isLastVersion = true;
    private UUID rootVersionID;
    private UUID fishkaID;
    @OneToOne
    private ContentFile readyPDF;
    private UUID journalID;
    private String number;
    private LocalDate registeredAt;

    public Missive(Long orgID, Sender sender, Signatory signatory, List<Confirmative> confirmatives,
                   Long departmentID, List<OutReceiver> outReceivers, List<InReceiver> inReceivers,
                   List<ContentFile> baseFiles, MissiveFile missiveFile, UUID fishkaID) {
        this.orgID = orgID;
        this.sender = sender;
        this.signatory = signatory;
        this.confirmatives = confirmatives;
        this.departmentID = departmentID;
        this.outReceivers = outReceivers;
        this.inReceivers = inReceivers;
        this.baseFiles = baseFiles;
        this.missiveFile = missiveFile;
        this.fishkaID = fishkaID;
    }
}
