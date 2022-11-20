package uz.bakhromjon.sender;

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
@Table(name = "sender")
public class Sender extends AbstractEntity {
    private UUID workPlaceID;
    private UUID userID;
    private Integer statusCode;
    private LocalDateTime statusTime = LocalDateTime.now();
    private Boolean isReadyToSend = false;


    public Sender(UUID workPlaceID, UUID userID, Integer statusCode) {
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.statusCode = statusCode;
    }
}
