package uz.bakhromjon.signatory;

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
@Table(name = "signatory")
public class Signatory extends AbstractEntity {
    private UUID workPlaceID;
    private UUID userID;
    private Integer statusCode;
    private LocalDateTime statusTime = LocalDateTime.now();
    private Boolean isSigned = false;
    private LocalDateTime signedAt;

    public Signatory(UUID workPlaceID, UUID userID, Integer statusCode) {
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.statusCode = statusCode;
    }
}
