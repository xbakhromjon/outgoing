package uz.bakhromjon.annotations;

import uz.bakhromjon.role.ERole;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRole {
    ERole value();
}
