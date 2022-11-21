package uz.bakhromjon.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Getter
@RequiredArgsConstructor
public enum ERole {
    ADMIN(0),
    BOSS_1(1),
    BOSS_2(2),
    BOSS_3(3),
    CHIEF_OF_GROUP(4),
    CONTROLLER(5),
    HEAD_OF_DEPARTMENT(6),
    HUMAN_RESOURCE(7),
    OFFICE_MANAGER(8),
    EMPLOYEE(9),
    SECURITY(10);


    private final Integer rank;
}
