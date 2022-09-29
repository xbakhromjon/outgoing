package uz.darico.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.darico.feign.obj.UserInfo;

/**
 * @author : Bakhromjon Khasanboyev
 * @user: xbakhromjon
 * @since : 29/09/22, Thu, 10:15
 **/
@RestController
@RequestMapping("/feign")
public class FeignController {
    private final DepartmentFeignService departmentFeignService;
    private final UserFeignService userFeignService;
    private final WorkPlaceFeignService workPlaceFeignService;

    public FeignController(DepartmentFeignService departmentFeignService, UserFeignService userFeignService, WorkPlaceFeignService workPlaceFeignService) {
        this.departmentFeignService = departmentFeignService;
        this.userFeignService = userFeignService;
        this.workPlaceFeignService = workPlaceFeignService;
    }

    @GetMapping("/department/{id}")
    public String department(@PathVariable Long id) {
        return departmentFeignService.getName(id);
    }

    @GetMapping("/workPlace/{id}")
    public Long workPlace(@PathVariable Long id) {
        return workPlaceFeignService.getUserID(id);
    }


    @GetMapping("/userInfo/{id}")
    public UserInfo userInfo(@PathVariable Long id) {
        return userFeignService.getUserInfo(id);
    }
}
