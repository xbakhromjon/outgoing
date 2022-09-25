package uz.darico.signatory;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "signatory")
public class Signatory extends AbstractEntity {
    private Long workPlaceID;
    private Long userID;
    private Integer statusCode;
    private Boolean isSigned = false;

    public Signatory(Long workPlaceID, Long userID, Integer statusCode) {
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.statusCode = statusCode;
    }
}
