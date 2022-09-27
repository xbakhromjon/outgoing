package uz.darico.confirmative;

import org.springframework.stereotype.Component;
import uz.darico.confirmative.dto.ConfirmativeShortInfoDTO;
import uz.darico.feign.UserFeignService;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.feign.obj.UserInfo;
import uz.darico.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;


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

    public List<Confirmative> toEntity(List<Long> confirmativeWorkPlaceIDs) {
        List<Confirmative> confirmatives = new ArrayList<>();
        for (int i = 0, confirmativeWorkPlaceIDsSize = confirmativeWorkPlaceIDs.size(); i < confirmativeWorkPlaceIDsSize; i++) {
            Long confirmativeWorkPlaceID = confirmativeWorkPlaceIDs.get(i);
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
                    .getStatusCode()));
            confirmativeShortInfoDTOs.add(confirmativeShortInfoDTO);
        }
        return confirmativeShortInfoDTOs;
    }
}
