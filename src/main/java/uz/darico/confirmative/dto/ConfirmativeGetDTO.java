package uz.darico.confirmative.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 03/10/22, Mon, 17:17
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmativeGetDTO {
    private UUID ID;
    private Long workPlaceID;
    private String firstName;
    private String lastName;
    private String middleName;
    private String status;
    private LocalDate statusTime;
    private Integer orderNumber;
}
