package uz.darico.missive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.outReceiver.dto.OutReceiverCreateDTO;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissiveCreateDTO {
    private UUID rootID;
    private Long orgID;
    private Long workPlaceID;
    private Long signatoryWorkPlaceID;
    private List<Long> confirmativeWorkPlaceIDs;
    private Long departmentID;
    private List<OutReceiverCreateDTO> outReceivers;
    private List<InReceiverCreateDTO> inReceivers;
    private List<UUID> baseFileIDs;
    private String content;
    private String shortInfo;
    private UUID fishkaID;
}
