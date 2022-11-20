package uz.bakhromjon.confirmative;

import org.springframework.stereotype.Component;
import uz.bakhromjon.confirmative.dto.ConfirmativeGetDTO;
import uz.bakhromjon.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.bakhromjon.feign.UserFeignService;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feign.obj.UserInfo;
import uz.bakhromjon.utils.BaseUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


@Component
public class ConfirmativeMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final UserFeignService userFeignService;
    private final BaseUtils baseUtils;

    public ConfirmativeMapper(WorkPlaceFeignService workPlaceFeignService, UserFeignService userFeignService,
                              BaseUtils baseUtils) {
        this.workPlaceFeignService = workPlaceFeignService;
        this.userFeignService = userFeignService;
        this.baseUtils = baseUtils;
    }

    public List<Confirmative> toEntity(List<UUID> confirmativeWorkPlaceIDs) {
        List<Confirmative> confirmatives = new ArrayList<>();
        for (int i = 0, confirmativeWorkPlaceIDsSize = confirmativeWorkPlaceIDs.size(); i < confirmativeWorkPlaceIDsSize; i++) {
            UUID confirmativeWorkPlaceID = confirmativeWorkPlaceIDs.get(i);
            Confirmative confirmative = new Confirmative(confirmativeWorkPlaceID,
                    workPlaceFeignService.getUserID(confirmativeWorkPlaceID),
                    ConfStatus.NOT_VIEWED.getCode(), i + 1);
            confirmatives.add(confirmative);
        }
        return confirmatives;
    }

    public List<ConfirmativeShortInfoDTO> toShortInfoDTO(List<Confirmative> confirmatives) {
        List<ConfirmativeShortInfoDTO> confirmativeShortInfoDTOs = new ArrayList<>();
        for (Confirmative confirmative : confirmatives) {
            UserInfo userInfo = userFeignService.getUserInfo(confirmative.getUserID());
            ConfirmativeShortInfoDTO confirmativeShortInfoDTO = new ConfirmativeShortInfoDTO(userInfo.getFirstName(), userInfo.getLastName(), baseUtils.convertConfStatusCodeToStr(confirmative
                    .getStatusCode()), confirmative.getOrderNumber());
            confirmativeShortInfoDTOs.add(confirmativeShortInfoDTO);
        }
        confirmativeShortInfoDTOs.sort(Comparator.comparing(ConfirmativeShortInfoDTO::getOrderNumber));
        return confirmativeShortInfoDTOs;
    }

    public ConfirmativeGetDTO toGetDTO(Confirmative confirmative) {
        if (confirmative == null) {
            return null;
        }
        UserInfo userInfo = userFeignService.getUserInfo(confirmative.getUserID());
        return new ConfirmativeGetDTO(confirmative.getId(), confirmative.getWorkPlaceID(), userInfo.getFirstName(), userInfo.getLastName(), userInfo.getMiddleName(),
                ConfStatus.toConfStatus(confirmative.getStatusCode()), confirmative.getStatusTime().toLocalDate(), confirmative.getOrderNumber());
    }

    public List<ConfirmativeGetDTO> toGetDTO(List<Confirmative> confirmatives) {
        List<ConfirmativeGetDTO> res = new ArrayList<>();
        confirmatives.forEach(item -> {
            res.add(toGetDTO(item));
        });
        res.sort(Comparator.comparing(ConfirmativeGetDTO::getOrderNumber));
        return res;
    }
}
