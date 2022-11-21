package uz.bakhromjon.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.bakhromjon.department.Department;
import uz.bakhromjon.department.DepartmentRepository;
import uz.bakhromjon.organization.Organization;
import uz.bakhromjon.organization.OrganizationRepository;
import uz.bakhromjon.permission.Permission;
import uz.bakhromjon.permission.PermissionRepository;
import uz.bakhromjon.role.ERole;
import uz.bakhromjon.role.Role;
import uz.bakhromjon.role.RoleRepository;
import uz.bakhromjon.user.User;
import uz.bakhromjon.user.UserRepository;
import uz.bakhromjon.workplace.WorkPlace;
import uz.bakhromjon.workplace.WorkPlaceRepository;

import java.util.Collection;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) {
        if (ddl.equalsIgnoreCase("create") || ddl.equalsIgnoreCase("create-drop")) {
            // organization
            organizationRepository.save(new Organization("Soliq", "Soliq", "Toshkent",
                    "+9987757777777", "soliq@gmail.com",
                    null, "https://www.soliq.com"));

            // role
            Role roleAdmin = roleRepository.save(new Role(ERole.ADMIN, ERole.ADMIN.getRank()));
            roleRepository.save(new Role(ERole.BOSS_1, ERole.BOSS_1.getRank()));
            roleRepository.save(new Role(ERole.BOSS_2, ERole.BOSS_2.getRank()));
            roleRepository.save(new Role(ERole.BOSS_3, ERole.BOSS_3.getRank()));
            roleRepository.save(new Role(ERole.CHIEF_OF_GROUP, ERole.CHIEF_OF_GROUP.getRank()));
            roleRepository.save(new Role(ERole.CONTROLLER, ERole.CONTROLLER.getRank()));
            roleRepository.save(new Role(ERole.HEAD_OF_DEPARTMENT, ERole.HEAD_OF_DEPARTMENT.getRank()));
            roleRepository.save(new Role(ERole.HUMAN_RESOURCE, ERole.HUMAN_RESOURCE.getRank()));
            roleRepository.save(new Role(ERole.OFFICE_MANAGER, ERole.OFFICE_MANAGER.getRank()));
            roleRepository.save(new Role(ERole.EMPLOYEE, ERole.EMPLOYEE.getRank()));
            roleRepository.save(new Role(ERole.SECURITY, ERole.SECURITY.getRank()));

            // permission
            permissionRepository.save(new Permission("RESOLUTION"));
            permissionRepository.save(new Permission("WIDE_SEARCH"));

            // rahbariyat
            Department department = departmentRepository.save(new Department("Rahbariyat", "admins", 1));

            // admin
            User admin = userRepository.save(new User("xbakhromjon", "xbakhromjon@gmail.com",
                    "123"));

            department.setUsers(Collections.singletonList(admin));
            departmentRepository.save(department);

            // workPlace
            WorkPlace workPlace = workPlaceRepository.save(new WorkPlace(admin, true, roleAdmin));
            department.setWorkPlaces(Collections.singletonList(workPlace));
            departmentRepository.save(department);
        }
    }
}
