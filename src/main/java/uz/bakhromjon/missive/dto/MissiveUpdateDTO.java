package uz.bakhromjon.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.inReceiver.dto.InReceiverCreateDTO;
import uz.bakhromjon.outReceiver.dto.OutReceiverCreateDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissiveUpdateDTO {
    private UUID ID;
    private UUID workPlaceID;
    private UUID signatoryWorkPlaceID;
    private List<UUID> confirmativeWorkPlaceIDs;
    private List<OutReceiverCreateDTO> outReceivers;
    private List<InReceiverCreateDTO> inReceivers;
    private List<UUID> baseFileIDs;
    private UUID missiveFileID;
    private String content;
    private String shortInfo;
}
