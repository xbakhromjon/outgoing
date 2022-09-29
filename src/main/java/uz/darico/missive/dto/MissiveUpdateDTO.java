package uz.darico.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.confirmative.Confirmative;
import uz.darico.contentFile.ContentFile;
import uz.darico.inReceiver.InReceiver;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.missiveFile.MissiveFile;
import uz.darico.outReceiver.OutReceiver;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;
import uz.darico.sender.Sender;
import uz.darico.signatory.Signatory;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissiveUpdateDTO {
    private UUID ID;
    private Long workPlaceID;
    private Long signatoryWorkPlaceID;
    private List<Long> confirmativeWorkPlaceIDs;
    private List<OutReceiverCreateDTO> outReceivers;
    private List<InReceiverCreateDTO> inReceivers;
    private List<UUID> baseFileIDs;
    private String content;
}
