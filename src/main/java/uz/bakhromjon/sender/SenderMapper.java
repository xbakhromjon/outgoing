package uz.bakhromjon.sender;

import org.springframework.stereotype.Component;
import uz.bakhromjon.feign.UserFeignService;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feign.obj.UserInfo;
import uz.bakhromjon.sender.dto.SenderGetDTO;

@Component
public class SenderMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final UserFeignService userFeignService;

    public SenderMapper(WorkPlaceFeignService workPlaceFeignService, UserFeignService userFeignService) {
        this.workPlaceFeignService = workPlaceFeignService;
        this.userFeignService = userFeignService;
    }

    public Sender toEntity(Long workPlaceID) {
        return new Sender(workPlaceID,
                workPlaceFeignService.getUserID(workPlaceID),
                SenderStatus.PREPARING.getCode());
    }

    public SenderGetDTO toGetDTO(Sender sender) {
        if (sender == null) {
            return null;
        }
        UserInfo userInfo = userFeignService.getUserInfo(sender.getUserID());
        return new SenderGetDTO(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getMiddleName(),
                SenderStatus.toSenderStatus(sender.getStatusCode()), sender.getStatusTime().toLocalDate());
    }
}
