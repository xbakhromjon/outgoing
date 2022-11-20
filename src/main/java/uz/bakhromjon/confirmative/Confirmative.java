package uz.bakhromjon.confirmative;

import lombok.*;
import uz.bakhromjon.base.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "confirmative")
public class Confirmative extends AbstractEntity {
    private UUID workPlaceID;
    private UUID userID;
    private Integer statusCode;
    private LocalDateTime statusTime = LocalDateTime.now();
    private Integer orderNumber;
    private Boolean isReadyToSend = false;

    public Confirmative(UUID workPlaceID, UUID userID, Integer statusCode, Integer orderNumber) {
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.statusCode = statusCode;
        this.orderNumber = orderNumber;
    }
}
