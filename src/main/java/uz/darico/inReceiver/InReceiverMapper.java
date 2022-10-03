package uz.darico.inReceiver;

import org.springframework.stereotype.Component;
import uz.darico.confirmative.ConfStatus;
import uz.darico.confirmative.Confirmative;
import uz.darico.confirmative.dto.ConfirmativeGetDTO;
import uz.darico.feign.OrganizationFeignService;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.feign.obj.UserInfo;
import uz.darico.inReceiver.dto.InReceiverCreateDTO;
import uz.darico.inReceiver.dto.InReceiverGetDTO;
import uz.darico.outReceiver.dto.OutReceiverGetDTO;


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
        String orgName = organizationFeignService.getName(inReceiver.getCorrespondentID());
        return new InReceiverGetDTO(orgName);
    }

    public List<InReceiverGetDTO> toGetDTO(List<InReceiver> inReceivers) {
        List<InReceiverGetDTO> res = new ArrayList<>();
        inReceivers.forEach(item -> {
            res.add(toGetDTO(item));
        });
        return res;
    }
}
