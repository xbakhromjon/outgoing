package uz.bakhromjon.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.bakhromjon.role.ERole;
import uz.bakhromjon.role.Role;
import uz.bakhromjon.role.RoleRepository;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) {
        if (ddl.equalsIgnoreCase("create") || ddl.equalsIgnoreCase("create-drop")) {
            roleRepository.save(new Role(ERole.BASE_ADMIN, ERole.BASE_ADMIN.getRank()));
            roleRepository.save(new Role(ERole.ADMIN, ERole.ADMIN.getRank()));
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
        }
    }
}
