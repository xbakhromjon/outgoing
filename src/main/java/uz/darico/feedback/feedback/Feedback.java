package uz.darico.feedback.feedback;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;
import uz.darico.feedback.conf.ConfFeedback;
import uz.darico.feedback.signatory.SignatoryFeedback;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback extends AbstractEntity {
    private UUID rootMissiveID;
    private Long workPlaceID;
    @OneToMany
    private List<ConfFeedback> confFeedbacks = new ArrayList<>();

    @OneToMany
    private List<SignatoryFeedback> signatoryFeedbacks = new ArrayList<>();

    public Feedback(UUID rootMissiveID, Long workPlaceID) {
        this.rootMissiveID = rootMissiveID;
        this.workPlaceID = workPlaceID;
    }
}
