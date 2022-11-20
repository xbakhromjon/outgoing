package uz.bakhromjon.feedback.signatory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 * @since : 28/10/22, Fri, 18:10
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignatoryFeedbackGetDTO {
    private UUID missiveID;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDateTime rejectedAt;
    private String content;
}
