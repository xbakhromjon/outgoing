package uz.darico.sender;

import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import uz.darico.feign.UserFeignService;
import uz.darico.feign.WorkPlaceFeignService;
import uz.darico.base.mapper.BaseMapper;
import uz.darico.feign.obj.UserInfo;
import uz.darico.sender.dto.SenderGetDTO;
import uz.darico.utils.BaseUtils;

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
