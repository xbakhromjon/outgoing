package uz.bakhromjon.sender;

import org.springframework.stereotype.Component;
import uz.bakhromjon.base.mapper.BaseMapper;
import uz.bakhromjon.feign.WorkPlaceFeignService;
import uz.bakhromjon.feign.obj.UserInfo;
import uz.bakhromjon.sender.dto.SenderGetDTO;
import uz.bakhromjon.user.UserService;

import java.util.UUID;

@Component
public class SenderMapper implements BaseMapper {
    private final WorkPlaceFeignService workPlaceFeignService;
    private final UserService userService;

    public SenderMapper(WorkPlaceFeignService workPlaceFeignService, UserService userService) {
        this.workPlaceFeignService = workPlaceFeignService;
        this.userService = userService;
    }

    public Sender toEntity(UUID workPlaceID) {
        return new Sender(workPlaceID,
                workPlaceFeignService.getUserID(workPlaceID),
                SenderStatus.PREPARING.getCode());
    }

    public SenderGetDTO toGetDTO(Sender sender) {
        if (sender == null) {
            return null;
        }
        UserInfo userInfo = userService.getUserInfo(sender.getUserID());
        return new SenderGetDTO(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getMiddleName(),
                SenderStatus.toSenderStatus(sender.getStatusCode()), sender.getStatusTime().toLocalDate());
    }
}
