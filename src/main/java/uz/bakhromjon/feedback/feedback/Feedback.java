package uz.bakhromjon.feedback.feedback;

import lombok.*;
import uz.bakhromjon.base.entity.AbstractEntity;
import uz.bakhromjon.feedback.conf.ConfFeedback;
import uz.bakhromjon.feedback.signatory.SignatoryFeedback;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
    private UUID workPlaceID;
    @OneToMany
    private List<ConfFeedback> confFeedbacks = new ArrayList<>();

    @OneToMany
    private List<SignatoryFeedback> signatoryFeedbacks = new ArrayList<>();

    public Feedback(UUID rootMissiveID, UUID workPlaceID) {
        this.rootMissiveID = rootMissiveID;
        this.workPlaceID = workPlaceID;
    }
}
