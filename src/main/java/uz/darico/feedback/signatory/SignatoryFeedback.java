package uz.darico.feedback.signatory;

import lombok.*;
import uz.darico.base.entity.AbstractEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SignatoryFeedback extends AbstractEntity {
    private UUID signatoryID;
    private LocalDateTime rejectedAt;
    private String content;
}
