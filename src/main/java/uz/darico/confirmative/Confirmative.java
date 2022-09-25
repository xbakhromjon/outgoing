package uz.darico.confirmative;

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
@Table(name = "confirmative")
public class Confirmative extends AbstractEntity {
    private Long workPlaceID;
    private Long userID;
    private Integer statusCode;
    private Integer orderNumber;
    private Boolean isReadyToSend = false;

    public Confirmative(Long workPlaceID, Long userID, Integer statusCode, Integer orderNumber) {
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.statusCode = statusCode;
        this.orderNumber = orderNumber;
    }
}
