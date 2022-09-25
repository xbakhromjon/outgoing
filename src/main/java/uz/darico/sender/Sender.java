package uz.darico.sender;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sender")
public class Sender extends AbstractEntity {
    private Long workPlaceID;
    private Long userID;
    private Integer statusCode;
    private Boolean isReadyToSend = false;

    public Sender(Long workPlaceID, Long userID, Integer statusCode) {
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.statusCode = statusCode;
    }
}
