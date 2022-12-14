package uz.bakhromjon.outReceiver;

import org.springframework.stereotype.Component;
import uz.bakhromjon.feign.OrganizationFeignService;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.outReceiver.dto.OutReceiverCreateDTO;
import uz.bakhromjon.outReceiver.dto.OutReceiverGetDTO;
import uz.bakhromjon.utils.OrgShortInfo;

import java.util.ArrayList;
import java.util.List;


@Component
public class OutReceiverMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final OrganizationFeignService organizationFeignService;

    public OutReceiverMapper(WorkPlaceFeignService workPlaceFeignService, OrganizationFeignService organizationFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
        this.organizationFeignService = organizationFeignService;
    }

    public List<OutReceiver> toEntity(List<OutReceiverCreateDTO> createDTOs) {
        List<OutReceiver> outReceivers = new ArrayList<>();
        for (OutReceiverCreateDTO createDTO : createDTOs) {
            OutReceiver outReceiver = new OutReceiver(createDTO.getCorrespondentID(),
                    createDTO.getCorrespondentEmail(), createDTO.getCorrespondentExat());
            outReceivers.add(outReceiver);
        }
        return outReceivers;
    }

    public OutReceiverGetDTO toGetDTO(OutReceiver outReceiver) {
        if (outReceiver == null) {
            return null;
        }
        OrgShortInfo orgShortInfo = organizationFeignService.getShortInfo(outReceiver.getCorrespondentID());
        return new OutReceiverGetDTO(orgShortInfo.getName(), orgShortInfo.getEmail());
    }

    public List<OutReceiverGetDTO> toGetDTO(List<OutReceiver> outReceivers) {
        List<OutReceiverGetDTO> res = new ArrayList<>();
        outReceivers.forEach(item -> {
            res.add(toGetDTO(item));
        });
        return res;
    }

    public List<OutReceiverCreateDTO> toCreateDTO(List<OutReceiver> outReceivers) {
        List<OutReceiverCreateDTO> outReceiverCreateDTOs = new ArrayList<>();
        for (OutReceiver outReceiver : outReceivers) {
            OutReceiverCreateDTO outReceiverCreateDTO = new OutReceiverCreateDTO(outReceiver.getCorrespondentID(), outReceiver.getCorrespondentEmail(), outReceiver.getCorrespondentExat());
            outReceiverCreateDTOs.add(outReceiverCreateDTO);
        }
        return outReceiverCreateDTOs;
    }
}
