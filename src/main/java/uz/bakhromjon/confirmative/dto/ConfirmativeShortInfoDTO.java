package uz.bakhromjon.confirmative.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmativeShortInfoDTO {
    private String firstName;
    private String lastName;
    private String status;
    private Integer orderNumber;
}
