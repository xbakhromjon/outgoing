package uz.bakhromjon.inReceiver;

import org.springframework.stereotype.Component;
import uz.bakhromjon.feign.OrganizationFeignService;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.inReceiver.dto.InReceiverCreateDTO;
import uz.bakhromjon.inReceiver.dto.InReceiverGetDTO;
import uz.bakhromjon.utils.OrgShortInfo;


import java.util.ArrayList;
import java.util.List;


@Component
public class InReceiverMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final OrganizationFeignService organizationFeignService;

    public InReceiverMapper(WorkPlaceFeignService workPlaceFeignService, OrganizationFeignService organizationFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
        this.organizationFeignService = organizationFeignService;
    }

    public List<InReceiver> toEntity(List<InReceiverCreateDTO> createDTOs) {
        List<InReceiver> inReceivers = new ArrayList<>();
        for (InReceiverCreateDTO createDTO : createDTOs) {
            InReceiver inReceiver = new InReceiver(createDTO.getCorrespondentID());
            inReceivers.add(inReceiver);
        }
        return inReceivers;
    }

    public InReceiverGetDTO toGetDTO(InReceiver inReceiver) {
        if (inReceiver == null) {
            return null;
        }
        OrgShortInfo orgShortInfo = organizationFeignService.getShortInfo(inReceiver.getCorrespondentID());
        return new InReceiverGetDTO(orgShortInfo.getName(), orgShortInfo.getEmail());
    }

    public List<InReceiverGetDTO> toGetDTO(List<InReceiver> inReceivers) {
        List<InReceiverGetDTO> res = new ArrayList<>();
        inReceivers.forEach(item -> {
            res.add(toGetDTO(item));
        });
        return res;
    }

    public List<InReceiverCreateDTO> toCreateDTO(List<InReceiver> inReceivers) {
        List<InReceiverCreateDTO> inReceiverCreateDTOs = new ArrayList<>();
        for (InReceiver inReceiver : inReceivers) {
            InReceiverCreateDTO inReceiverCreateDTO = new InReceiverCreateDTO(inReceiver.getCorrespondentID());
            inReceiverCreateDTOs.add(inReceiverCreateDTO);
        }
        return inReceiverCreateDTOs;
    }
}
