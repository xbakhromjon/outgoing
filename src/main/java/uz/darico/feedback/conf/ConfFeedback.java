package uz.darico.feedback.conf;

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
public class ConfFeedback extends AbstractEntity {
    private UUID confID;
    private LocalDateTime rejectedAt;
    private String content;
}
