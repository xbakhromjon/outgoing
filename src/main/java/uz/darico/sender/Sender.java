package uz.darico.sender;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.feedback.conf.ConfFeedback;
import uz.darico.feedback.signatory.SignatoryFeedback;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime statusTime = LocalDateTime.now();
    private Boolean isReadyToSend = false;


    public Sender(Long workPlaceID, Long userID, Integer statusCode) {
        this.workPlaceID = workPlaceID;
        this.userID = userID;
        this.statusCode = statusCode;
    }
}
