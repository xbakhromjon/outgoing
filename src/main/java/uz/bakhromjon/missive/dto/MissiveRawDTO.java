package uz.bakhromjon.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.contentFile.ContentFile;
import uz.bakhromjon.fishka.dto.FishkaGetDTO;
import uz.bakhromjon.inReceiver.dto.InReceiverCreateDTO;
import uz.bakhromjon.outReceiver.dto.OutReceiverCreateDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissiveRawDTO {
    private UUID ID;
    private UUID rootID;
    private UUID orgID;
    private UUID workPlace;
    private UUID signatoryWorkPlace;
    private List<UUID> confirmativeWorkPlaces;
    private List<OutReceiverCreateDTO> outReceivers;
    private List<InReceiverCreateDTO> inReceivers;
    private List<ContentFile> baseFiles;
    private UUID missiveFileID;
    private String missiveContent;
    private FishkaGetDTO fishkaGetDTO;
}
