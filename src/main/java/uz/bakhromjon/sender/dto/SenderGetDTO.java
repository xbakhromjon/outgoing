package uz.bakhromjon.sender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 03/10/22, Mon, 17:15
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SenderGetDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String status;
    private LocalDate statusTime;
}
