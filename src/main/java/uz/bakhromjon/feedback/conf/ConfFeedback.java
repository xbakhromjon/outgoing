package uz.bakhromjon.feedback.conf;

import lombok.*;
import uz.bakhromjon.base.entity.AbstractEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConfFeedback extends AbstractEntity {
    private UUID missiveID;
    private Long workPlaceID;
    private LocalDateTime rejectedAt;
    private String content;
}
