package uz.bakhromjon.annotations;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.role.Role;
import uz.bakhromjon.user.User;
import uz.bakhromjon.user.UserDetailsImpl;
import uz.bakhromjon.workplace.WorkPlaceService;


@Component
@Aspect
public class CheckRoleExecutor {
    @Autowired
    private WorkPlaceService workPlaceService;

    @Before(value = "@annotation(checkRole)")
    public void checkRoleMethod(CheckRole checkRole) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role role = workPlaceService.getRoleByUser(user.getId());
        if (!role.getRank().equals(checkRole.value().getRank())) {
            throw new UniversalException("Access Denied", HttpStatus.FORBIDDEN);
        }
    }
}
