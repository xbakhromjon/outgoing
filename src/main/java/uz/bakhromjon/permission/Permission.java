package uz.bakhromjon.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bakhromjon.base.entity.AbstractEntity;

import javax.persistence.Entity;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission extends AbstractEntity {
    private String name;
}
