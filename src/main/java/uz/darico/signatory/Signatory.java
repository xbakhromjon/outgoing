package uz.darico.signatory;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
    private LocalDateTime statusTime = LocalDateTime.now();
    private Boolean isSigned = false;
    private LocalDateTime signedAt;

    public Signatory(Long workPlaceID, Long userID, Integer statusCode) {
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.statusCode = statusCode;
    }
}
